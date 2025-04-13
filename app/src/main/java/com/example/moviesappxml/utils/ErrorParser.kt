package com.example.moviesappxml.utils

import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import com.example.moviesappxml.common.data.models.ErrorResponse
import com.example.moviesappxml.common.ui.models.ErrorModel
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

object ErrorParser {
    fun getLoadStateError(loadState: CombinedLoadStates?): Throwable? {
        return (loadState?.append as? LoadState.Error)?.error
            ?: (loadState?.prepend as? LoadState.Error)?.error
            ?: (loadState?.refresh as? LoadState.Error)?.error
    }

    fun isLoadStateError(loadState: CombinedLoadStates?): Boolean {
        return loadState?.append is LoadState.Error || loadState?.prepend is LoadState.Error || loadState?.refresh is LoadState.Error
    }

    fun parseThrowable(throwable: Throwable): ErrorModel {
        return when (throwable) {
            is IOException ->
                ErrorModel.InternetConnectionError

            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                if (!errorBody.isNullOrEmpty()) {
                    try {
                        val errorResponse =
                            Json.decodeFromString<ErrorResponse>(errorBody)
                        if (errorResponse.statusMessage == null)
                            ErrorModel.UnknownError
                        else
                            ErrorModel.RequestError(errorResponse.statusMessage)
                    } catch (e: Exception) {
                        ErrorModel.UnknownError
                    }
                } else {
                    ErrorModel.UnknownError
                }
            }

            else -> {
                ErrorModel.UnknownError
            }
        }
    }

}