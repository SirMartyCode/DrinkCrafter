package com.sirmarty.drinkcrafter.domain.usecase

import com.sirmarty.drinkcrafter.domain.entity.Drink
import com.sirmarty.drinkcrafter.domain.repository.SearchRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SearchDrinkByNameUseCaseTest {

    @RelaxedMockK
    private lateinit var searchDrinkRepository: SearchRepository
    private lateinit var searchDrinkByNameUseCase: SearchDrinkByNameUseCase

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        searchDrinkByNameUseCase = SearchDrinkByNameUseCase(searchDrinkRepository)
    }

    @Test
    fun `when text is empty return empty list`() = runBlocking {
        // Given
        val text = ""

        // When
        val response = searchDrinkByNameUseCase.execute(text)

        // Then
        coVerify(exactly = 0) { searchDrinkRepository.searchDrinkByName(any()) }
        assert(response == emptyList<Drink>())
    }

    @Test
    fun `when text is not empty return list from api`() = runBlocking {
        // Given
        val text = "drink"
        val drinkList = listOf(
            Drink(1, "drink1", "image1"),
            Drink(2, "drink2", "image2")
        )
        coEvery { searchDrinkRepository.searchDrinkByName(text) } returns drinkList

        // When
        val response = searchDrinkByNameUseCase.execute(text)

        // Then
        coVerify(exactly = 1) { searchDrinkRepository.searchDrinkByName(any()) }
        assert(response == drinkList)
    }
}