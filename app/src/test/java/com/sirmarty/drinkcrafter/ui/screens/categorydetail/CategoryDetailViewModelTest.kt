package com.sirmarty.drinkcrafter.ui.screens.categorydetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListByCategoryUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
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
class CategoryDetailViewModelTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var getDrinkListByCategoryUseCase: GetDrinkListByCategoryUseCase
    private lateinit var categoryDetailViewModel: CategoryDetailViewModel

    private val drinkList: List<Drink> = listOf(
        Drink(1, "drink1", "image1"),
        Drink(2, "drink2", "image2"),
        Drink(3, "drink3", "image3")
    )
    private val categoryName: String = "category1"

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        categoryDetailViewModel = CategoryDetailViewModel(getDrinkListByCategoryUseCase)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when category detail requested return drinks from use case`() = runTest {
        // Given
        coEvery { getDrinkListByCategoryUseCase.execute(any()) } returns drinkList

        // When
        categoryDetailViewModel.getDrinkList(categoryName)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getDrinkListByCategoryUseCase.execute(any()) }
        assertEquals(categoryDetailViewModel.uiState.value, UiState.Success(drinkList))
        assertEquals(categoryDetailViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when requesting same category detail twice the use case is just called once`() =
        runTest {
            // Given
            coEvery { getDrinkListByCategoryUseCase.execute(any()) } returns drinkList

            // When
            categoryDetailViewModel.getDrinkList(categoryName)
            advanceUntilIdle()
            categoryDetailViewModel.getDrinkList(categoryName)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) { getDrinkListByCategoryUseCase.execute(any()) }
            assertEquals(categoryDetailViewModel.uiState.value, UiState.Success(drinkList))
            assertEquals(categoryDetailViewModel.showErrorDialog.value, false)
        }

    @Test
    fun `when retrying the request the use case is called twice`() = runTest {
        // Given
        coEvery { getDrinkListByCategoryUseCase.execute(any()) } returns drinkList

        // When
        categoryDetailViewModel.getDrinkList(categoryName)
        advanceUntilIdle()
        categoryDetailViewModel.retryRequest()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getDrinkListByCategoryUseCase.execute(any()) }
        assertEquals(categoryDetailViewModel.uiState.value, UiState.Success(drinkList))
        assertEquals(categoryDetailViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when use case returns error view model shows error dialog`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getDrinkListByCategoryUseCase.execute(any()) } throws exception

        // When
        categoryDetailViewModel.getDrinkList(categoryName)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getDrinkListByCategoryUseCase.execute(any()) }
        assertEquals(categoryDetailViewModel.uiState.value, UiState.Error(exception))
        assertEquals(categoryDetailViewModel.showErrorDialog.value, true)
    }

}