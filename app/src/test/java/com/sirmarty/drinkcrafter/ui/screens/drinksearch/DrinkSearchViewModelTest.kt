package com.sirmarty.drinkcrafter.ui.screens.drinksearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.usecase.SearchDrinkByNameUseCase
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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@OptIn(ExperimentalCoroutinesApi::class)
class DrinkSearchViewModelTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var searchDrinkByNameUseCase: SearchDrinkByNameUseCase
    private lateinit var drinkSearchViewModel: DrinkSearchViewModel

    private val query: String = "query"
    private val drinkSearchResult: List<Drink> = listOf(
        Drink(1, "drink1", "image1"),
        Drink(2, "drink2", "image2"),
        Drink(3, "drink3", "image3")
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        drinkSearchViewModel = DrinkSearchViewModel(searchDrinkByNameUseCase)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when query changes return drink coincidences from use case`() = runTest {
        // Given
        coEvery { searchDrinkByNameUseCase.execute(any()) } returns drinkSearchResult

        // When
        drinkSearchViewModel.onQueryChanged(query)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { searchDrinkByNameUseCase.execute(any()) }
        assertEquals(drinkSearchViewModel.uiState.value, UiState.Success(drinkSearchResult))
        assertEquals(drinkSearchViewModel.query.value, query)
        assertEquals(drinkSearchViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when search is cleared use case returns empty list`() = runTest {
        // Given

        // When
        drinkSearchViewModel.clearSearch()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { searchDrinkByNameUseCase.execute(any()) }
        assertEquals(drinkSearchViewModel.uiState.value, UiState.Success(emptyList<Drink>()))
        assertEquals(drinkSearchViewModel.query.value, "")
        assertEquals(drinkSearchViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when retry request called return same search result as normal search`() = runTest {
        // Given
        coEvery { searchDrinkByNameUseCase.execute(any()) } returns drinkSearchResult

        // When
        drinkSearchViewModel.onQueryChanged(query)
        advanceUntilIdle()
        drinkSearchViewModel.retryRequest()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { searchDrinkByNameUseCase.execute(any()) }
        assertEquals(drinkSearchViewModel.uiState.value, UiState.Success(drinkSearchResult))
        assertEquals(drinkSearchViewModel.query.value, query)
        assertEquals(drinkSearchViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when use case returns error view model shows error dialog`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { searchDrinkByNameUseCase.execute(any()) } throws exception

        // When
        drinkSearchViewModel.onQueryChanged(query)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { searchDrinkByNameUseCase.execute(any()) }
        TestCase.assertEquals(drinkSearchViewModel.uiState.value, UiState.Error(exception))
        TestCase.assertEquals(drinkSearchViewModel.showErrorDialog.value, true)
    }
}