package com.sirmarty.drinkcrafter.ui.screens.categories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sirmarty.drinkcrafter.R
import com.sirmarty.drinkcrafter.domain.entity.Category
import com.sirmarty.drinkcrafter.domain.usecase.GetCategoryListUseCase
import com.sirmarty.drinkcrafter.ui.model.CategoryWithImage
import com.sirmarty.drinkcrafter.ui.screens.UiState
import com.sirmarty.drinkcrafter.ui.screens.categorylist.CategoryImageMapper
import com.sirmarty.drinkcrafter.ui.screens.categorylist.CategoryListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class CategoryListViewModelTest {

    /**
     * Since getCategoryListUseCase is called every time the view model is created, in each test
     * that checks how many times the use case is called we'll have to add 1 to the expected amount
     */

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var getCategoryListUseCase: GetCategoryListUseCase

    @RelaxedMockK
    private lateinit var categoryImageMapper: CategoryImageMapper
    private lateinit var categoryListViewModel: CategoryListViewModel

    private val categoryList = listOf(
        Category("category1"),
        Category("category2")
    )
    private val categoriesWithImages = listOf(
        CategoryWithImage(categoryList[0].name, R.drawable.image_default_category),
        CategoryWithImage(categoryList[1].name, R.drawable.image_default_category)
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        categoryListViewModel = CategoryListViewModel(getCategoryListUseCase, categoryImageMapper)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model created get categories is called`() = runTest {
        // Given
        coEvery { getCategoryListUseCase.execute() } returns categoryList
        categoryList.forEachIndexed { index, category ->
            every {
                categoryImageMapper.getCategoryWithImageForCategoryName(category.name)
            } returns categoriesWithImages[index]
        }

        // When
        categoryListViewModel = CategoryListViewModel(getCategoryListUseCase, categoryImageMapper)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getCategoryListUseCase.execute() }
        coVerify(exactly = 2) { categoryImageMapper.getCategoryWithImageForCategoryName(any()) }
        assertEquals(categoryListViewModel.uiState.value, UiState.Success(categoriesWithImages))
        assertEquals(categoryListViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when retry request called return categories from use case`() = runTest {
        // Given
        coEvery { getCategoryListUseCase.execute() } returns categoryList
        categoryList.forEachIndexed { index, category ->
            every {
                categoryImageMapper.getCategoryWithImageForCategoryName(category.name)
            } returns categoriesWithImages[index]
        }

        // When
        categoryListViewModel.retryRequest()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getCategoryListUseCase.execute() }
        coVerify(exactly = 2) { categoryImageMapper.getCategoryWithImageForCategoryName(any()) }
        assertEquals(categoryListViewModel.uiState.value, UiState.Success(categoriesWithImages))
        assertEquals(categoryListViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when use case returns error view model shows error dialog`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getCategoryListUseCase.execute() } throws exception

        // When
        categoryListViewModel.retryRequest()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getCategoryListUseCase.execute() }
        coVerify(exactly = 0) { categoryImageMapper.getCategoryWithImageForCategoryName(any()) }
        assertEquals(categoryListViewModel.uiState.value, UiState.Error(exception))
        assertEquals(categoryListViewModel.showErrorDialog.value, true)
    }

}