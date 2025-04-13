package com.example.moviesappxml.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.LoadStates
import com.example.moviesappxml.common.ui.models.ErrorModel
import com.example.moviesappxml.utils.ErrorParser.getLoadStateError
import com.example.moviesappxml.utils.ErrorParser.isLoadStateError
import com.example.moviesappxml.utils.ErrorParser.parseThrowable
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

class ErrorParserTest {

    @Test
    fun getLoadStateError_loadState_errorMessage() {
        val loadState = CombinedLoadStates(
            refresh = LoadState.Error(Exception("Test error")),
            append = LoadState.NotLoading(endOfPaginationReached = false),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.Error(Exception("Test error")),
                append = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
        val error = getLoadStateError(loadState)
        assertNotNull(error)
    }


    @Test
    fun isLoadStateError_loadState_doesExist() {
        val loadState = CombinedLoadStates(
            refresh = LoadState.Error(Exception("Test error")),
            append = LoadState.NotLoading(endOfPaginationReached = false),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.Error(Exception("Test error")),
                append = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
        val isError = isLoadStateError(loadState)
        assertTrue(isError)
    }

    @Test
    fun isLoadStateError_loadState_doesntExist() {
        val loadState = CombinedLoadStates(
            refresh = LoadState.NotLoading(endOfPaginationReached = true),
            append = LoadState.NotLoading(endOfPaginationReached = true),
            prepend = LoadState.NotLoading(endOfPaginationReached = true),
            source = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true),
                prepend = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )
        val isError = isLoadStateError(loadState)
        assertFalse(isError)
    }


    @Test
    fun parseThrowable_noNetworkError_returnsStringWrapper() {
        val throwable = IOException("Internet connection error")
        val errorModel = parseThrowable(throwable)
        assertTrue(errorModel is ErrorModel.InternetConnectionError)
    }
}