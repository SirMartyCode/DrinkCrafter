package com.sirmarty.drinkcrafter.ui.screens.ingredientdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.entity.IngredientDetail
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkListByIngredientUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetIngredientDetailUseCase
import com.sirmarty.drinkcrafter.ui.components.customtopappbar.CustomTopAppBarState
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
class IngredientDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var getIngredientDetailUseCase: GetIngredientDetailUseCase

    @RelaxedMockK
    private lateinit var getDrinkListByIngredientUseCase: GetDrinkListByIngredientUseCase
    private lateinit var ingredientDetailViewModel: IngredientDetailViewModel

    private val ingredientName: String = "ingredient1"
    private val ingredientDetail: IngredientDetail = IngredientDetail(
        1, ingredientName, "description", "type1", true, "30"
    )
    private val drinkList: List<Drink> = listOf(
        Drink(1, "drink1", "image1"),
        Drink(2, "drink2", "image2"),
        Drink(3, "drink3", "image3")
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        ingredientDetailViewModel =
            IngredientDetailViewModel(getIngredientDetailUseCase, getDrinkListByIngredientUseCase)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when get data is called return combined data from both use cases`() = runTest {
        // Given
        coEvery { getIngredientDetailUseCase.execute(any()) } returns ingredientDetail
        coEvery { getDrinkListByIngredientUseCase.execute(any()) } returns drinkList

        // When
        ingredientDetailViewModel.getData(ingredientName)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getIngredientDetailUseCase.execute(any()) }
        coVerify(exactly = 1) { getDrinkListByIngredientUseCase.execute(any()) }
        assertEquals(
            ingredientDetailViewModel.uiState.value,
            UiState.Success(Pair(ingredientDetail, drinkList))
        )
        assertEquals(ingredientDetailViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when requesting same ingredient detail twice the use case is just called once`() =
        runTest {
            // Given
            coEvery { getIngredientDetailUseCase.execute(any()) } returns ingredientDetail
            coEvery { getDrinkListByIngredientUseCase.execute(any()) } returns drinkList

            // When
            ingredientDetailViewModel.getData(ingredientName)
            advanceUntilIdle()
            ingredientDetailViewModel.getData(ingredientName)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) { getIngredientDetailUseCase.execute(any()) }
            coVerify(exactly = 1) { getDrinkListByIngredientUseCase.execute(any()) }
            assertEquals(
                ingredientDetailViewModel.uiState.value,
                UiState.Success(Pair(ingredientDetail, drinkList))
            )
            assertEquals(ingredientDetailViewModel.showErrorDialog.value, false)
        }

    @Test
    fun `when retrying the request the use case is called twice`() = runTest {
        // Given
        coEvery { getIngredientDetailUseCase.execute(any()) } returns ingredientDetail
        coEvery { getDrinkListByIngredientUseCase.execute(any()) } returns drinkList

        // When
        ingredientDetailViewModel.getData(ingredientName)
        advanceUntilIdle()
        ingredientDetailViewModel.retryRequest()
        advanceUntilIdle()

        // Then
        coVerify(exactly = 2) { getIngredientDetailUseCase.execute(any()) }
        coVerify(exactly = 2) { getDrinkListByIngredientUseCase.execute(any()) }
        assertEquals(
            ingredientDetailViewModel.uiState.value,
            UiState.Success(Pair(ingredientDetail, drinkList))
        )
        assertEquals(ingredientDetailViewModel.showErrorDialog.value, false)
    }

    @Test
    fun `when view model created top bar state is solid`() = runTest {
        // Given

        // When
        ingredientDetailViewModel =
            IngredientDetailViewModel(getIngredientDetailUseCase, getDrinkListByIngredientUseCase)
        advanceUntilIdle()

        // Then
        assertEquals(ingredientDetailViewModel.topAppBarState.value, CustomTopAppBarState.SOLID)
    }

    @Test
    fun `when app bar is above the title top bar state is solid`() = runTest {
        // Given

        // When
        ingredientDetailViewModel.updateAppBarHeight(50f)
        ingredientDetailViewModel.updateTitleBottomOffset(70f)

        // Then
        assertEquals(ingredientDetailViewModel.topAppBarState.value, CustomTopAppBarState.SOLID)
    }

    @Test
    fun `when app bar is below the title top bar state is solid with title`() = runTest {
        // Given

        // When
        ingredientDetailViewModel.updateAppBarHeight(50f)
        ingredientDetailViewModel.updateTitleBottomOffset(30f)

        // Then
        assertEquals(
            ingredientDetailViewModel.topAppBarState.value,
            CustomTopAppBarState.SOLID_WITH_TITLE
        )
    }

    @Test
    fun `when use case returns error view model shows error dialog`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getIngredientDetailUseCase.execute(any()) } throws exception

        // When
        ingredientDetailViewModel.getData(ingredientName)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getIngredientDetailUseCase.execute(any()) }
        coVerify(exactly = 0) { getDrinkListByIngredientUseCase.execute(any()) }
        assertEquals(ingredientDetailViewModel.uiState.value, UiState.Error(exception))
        assertEquals(ingredientDetailViewModel.showErrorDialog.value, true)
    }

}