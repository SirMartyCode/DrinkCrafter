package com.sirmarty.drinkcrafter.ui.screens.ingredientsearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sirmarty.drinkcrafter.domain.entity.IngredientName
import com.sirmarty.drinkcrafter.domain.usecase.GetIngredientListUseCase
import com.sirmarty.drinkcrafter.ui.screens.UiState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class IngredientSearchViewModelTest {

    /**
     * Since getIngredientListUseCase is called every time the view model is created, in each test
     * that checks how many times the use case is called we'll have to add 1 to the expected amount
     */

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var getIngredientListUseCase: GetIngredientListUseCase
    private lateinit var ingredientSearchViewModel: IngredientSearchViewModel

    private val query: String = "lime"
    private val ingredientList: List<IngredientName> = listOf(
        IngredientName("lime"),
        IngredientName("vodka"),
        IngredientName("tequila"),
        IngredientName("orange juice")
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        ingredientSearchViewModel = IngredientSearchViewModel(getIngredientListUseCase)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when view model created return all ingredients from the use case`() = runTest {
        // Given
        coEvery { getIngredientListUseCase.execute() } returns ingredientList

        // When
        ingredientSearchViewModel = IngredientSearchViewModel(getIngredientListUseCase)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getIngredientListUseCase.execute() }
        assertEquals(ingredientSearchViewModel.uiState.value, UiState.Success(ingredientList))
        assertEquals(ingredientSearchViewModel.query.value, null)
        assertEquals(ingredientSearchViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when query changes return ingredient coincidences from use case`() = runTest {
        // Given
        coEvery { getIngredientListUseCase.execute() } returns ingredientList
        val searchResult = ingredientList.filter { it.name == query }

        // When
        ingredientSearchViewModel = IngredientSearchViewModel(getIngredientListUseCase)
        advanceUntilIdle()
        ingredientSearchViewModel.onQueryChanged(query)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getIngredientListUseCase.execute() }
        assertEquals(ingredientSearchViewModel.uiState.value, UiState.Success(searchResult))
        assertEquals(ingredientSearchViewModel.query.value, query)
        assertEquals(ingredientSearchViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when search cleared return all ingredients from use case`() = runTest {
        // Given
        coEvery { getIngredientListUseCase.execute() } returns ingredientList

        // When
        ingredientSearchViewModel = IngredientSearchViewModel(getIngredientListUseCase)
        advanceUntilIdle()
        ingredientSearchViewModel.onQueryChanged(query)
        advanceUntilIdle()
        ingredientSearchViewModel.clearSearch()

        // Then
        coVerify(exactly = 2) { getIngredientListUseCase.execute() }
        assertEquals(ingredientSearchViewModel.uiState.value, UiState.Success(ingredientList))
        assertEquals(ingredientSearchViewModel.query.value, "")
        assertEquals(ingredientSearchViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when use case returns error view model shows error dialog`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getIngredientListUseCase.execute() } throws exception

        // When
        ingredientSearchViewModel.retryRequest()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getIngredientListUseCase.execute() }
        TestCase.assertEquals(ingredientSearchViewModel.uiState.value, UiState.Error(exception))
        TestCase.assertEquals(ingredientSearchViewModel.showErrorDialog.value, true)
    }
}