package com.sirmarty.drinkcrafter.ui.screens.drinkdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.entity.Ingredient
import com.sirmarty.drinkcrafter.domain.usecase.GetDrinkDetailUseCase
import com.sirmarty.drinkcrafter.domain.usecase.GetRandomDrinkDetailUseCase
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
class DrinkDetailViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()

    @RelaxedMockK
    private lateinit var getDrinkDetailUseCase: GetDrinkDetailUseCase

    @RelaxedMockK
    private lateinit var getRandomDrinkDetailUseCase: GetRandomDrinkDetailUseCase
    private lateinit var drinkDetailViewModel: DrinkDetailViewModel

    private val normalId = 0 // This could be any id from an existing drink
    private val randomId = -1 // This is the equivalent to ID_RANDOM_DRINK in DrinkDetailViewModel

    private val normalDrinkDetail = DrinkDetail(
        0, "normalDrink", "category1", "alcoholic", "normalGlass",
        "instructions", "image1", listOf(
            Ingredient("ingredient1", "measure1")
        )
    )

    private val randomDrinkDetail = DrinkDetail(
        1, "randomDrink", "category2", "alcoholic", "normalGlass",
        "instructions", "image2", listOf(
            Ingredient("ingredient2", "measure2")
        )
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        drinkDetailViewModel =
            DrinkDetailViewModel(getDrinkDetailUseCase, getRandomDrinkDetailUseCase)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when id is not random id return drink detail from normal drink detail use case`() =
        runTest {
            // Given
            coEvery { getDrinkDetailUseCase.execute(any()) } returns normalDrinkDetail

            // When
            drinkDetailViewModel.getDrinkDetail(normalId)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) { getDrinkDetailUseCase.execute(normalId) }
            coVerify(exactly = 0) { getRandomDrinkDetailUseCase.execute() }
            assertEquals(drinkDetailViewModel.uiState.value, UiState.Success(normalDrinkDetail))
            assertEquals(drinkDetailViewModel.showErrorDialog.value, false)
        }

    @Test
    fun `when id is random id return drink detail from random drink detail use case`() =
        runTest {
            // Given
            coEvery { getRandomDrinkDetailUseCase.execute() } returns randomDrinkDetail

            // When
            drinkDetailViewModel.getDrinkDetail(randomId)
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) { getRandomDrinkDetailUseCase.execute() }
            coVerify(exactly = 0) { getDrinkDetailUseCase.execute(any()) }
            assertEquals(drinkDetailViewModel.uiState.value, UiState.Success(randomDrinkDetail))
            assertEquals(drinkDetailViewModel.showErrorDialog.value, false)
        }

    @Test
    fun `when use case returns error view model shows error dialog`() = runTest {
        // Given
        val exception = Exception("Network error")
        coEvery { getDrinkDetailUseCase.execute(any()) } throws exception

        // When
        drinkDetailViewModel.getDrinkDetail(normalId)
        advanceUntilIdle()

        // Then
        coVerify(exactly = 1) { getDrinkDetailUseCase.execute(normalId) }
        coVerify(exactly = 0) { getRandomDrinkDetailUseCase.execute() }
        assertEquals(drinkDetailViewModel.uiState.value, UiState.Error(exception))
        assertEquals(drinkDetailViewModel.showErrorDialog.value, true)
    }

    @Test
    fun `when view model created top bar state is transparent`() = runTest {
        // Given

        // When
        drinkDetailViewModel =
            DrinkDetailViewModel(getDrinkDetailUseCase, getRandomDrinkDetailUseCase)
        advanceUntilIdle()

        // Then
        assertEquals(drinkDetailViewModel.topAppBarState.value, CustomTopAppBarState.TRANSPARENT)
    }

    @Test
    fun `when app bar height value is lower than image bottom and title bottom top bar state is transparent`() =
        runTest {
            // Given

            // When
            drinkDetailViewModel.updateAppBarHeight(10f)
            drinkDetailViewModel.updateImageBottomOffset(30f)
            drinkDetailViewModel.updateTitleBottomOffset(50f)

            // Then
            assertEquals(drinkDetailViewModel.topAppBarState.value, CustomTopAppBarState.TRANSPARENT)
        }

    @Test
    fun `when app bar height value is between image bottom and title bottom top bar state is solid`() =
        runTest {
            // Given

            // When
            drinkDetailViewModel.updateAppBarHeight(50f)
            drinkDetailViewModel.updateImageBottomOffset(30f)
            drinkDetailViewModel.updateTitleBottomOffset(70f)

            // Then
            assertEquals(drinkDetailViewModel.topAppBarState.value, CustomTopAppBarState.SOLID)
        }

    @Test
    fun `when app bar height value is higher than image bottom and title bottom top bar state is solid with title`() =
        runTest {
            // Given

            // When
            drinkDetailViewModel.updateAppBarHeight(50f)
            drinkDetailViewModel.updateImageBottomOffset(10f)
            drinkDetailViewModel.updateTitleBottomOffset(30f)

            // Then
            assertEquals(drinkDetailViewModel.topAppBarState.value, CustomTopAppBarState.SOLID_WITH_TITLE)
        }

}