package com.example.moviesappxml.details.domain.useCases

import com.example.moviesappxml.common.domain.useCases.ImagesUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class ImagesUseCaseTest {

    @Test
    fun invokePosterUseCase_getPosterUrl_null() {
        assertEquals("", ImagesUseCase().invoke(null))
    }

    @Test
    fun invokePosterUseCase_getPosterUrl_blank() {
        assertEquals("", ImagesUseCase().invoke(null))
    }

    @Test
    fun invokePosterUseCase_getPosterUrl_valid() {
        val imageSuffix = "12345"
        val result = ImagesUseCase().invoke(imageSuffix)
        assertEquals("https://image.tmdb.org/t/p/w200/$imageSuffix", result)
    }
}