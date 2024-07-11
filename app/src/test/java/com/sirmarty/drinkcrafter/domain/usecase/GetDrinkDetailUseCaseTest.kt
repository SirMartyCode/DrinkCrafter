package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.DrinkDetail
import com.sirmarty.drinkcrafter.domain.entity.Ingredient
import com.sirmarty.drinkcrafter.domain.repository.DrinkRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetDrinkDetailUseCaseTest {

    @RelaxedMockK
    private lateinit var drinkRepository: DrinkRepository
    private lateinit var isSavedUseCase: IsSavedUseCase
    private lateinit var getDrinkDetailUseCase: GetDrinkDetailUseCase

    private val drinkId = 1
    private val drinkDetail = DrinkDetail(
        id = drinkId,
        name = "drink1",
        category = "category",
        alcoholic = "non alcoholic",
        glass = "glass",
        instructions = "instructions",
        image = "image",
        ingredients = listOf(
            Ingredient("ingredient1", "measure1"),
            Ingredient("ingredient2", "measure2")
        )
    )

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        isSavedUseCase = IsSavedUseCase(drinkRepository)
        getDrinkDetailUseCase = GetDrinkDetailUseCase(isSavedUseCase, drinkRepository)
    }

    @Test
    fun `when drink is saved return drink detail from database`() = runBlocking {
        // Given
        every { isSavedUseCase.execute(drinkId) } returns flowOf(true)
        coEvery { drinkRepository.getSavedDrinkDetail(drinkId) } returns drinkDetail

        // When
        val response = getDrinkDetailUseCase.execute(drinkId)

        // Then
        coVerify(exactly = 1) { drinkRepository.getSavedDrinkDetail(any()) }
        coVerify(exactly = 0) { drinkRepository.getDrinkDetail(any()) }
        assert(response == drinkDetail)
    }

    @Test
    fun `when drink is not saved return drink detail from api`() = runBlocking {
        // Given
        every { isSavedUseCase.execute(drinkId) } returns flowOf(false)
        coEvery { drinkRepository.getDrinkDetail(drinkId) } returns drinkDetail

        // When
        val response = getDrinkDetailUseCase.execute(drinkId)

        // Then
        coVerify(exactly = 1) { drinkRepository.getDrinkDetail(any()) }
        coVerify(exactly = 0) { drinkRepository.getSavedDrinkDetail(any()) }
        assert(response == drinkDetail)
    }
}