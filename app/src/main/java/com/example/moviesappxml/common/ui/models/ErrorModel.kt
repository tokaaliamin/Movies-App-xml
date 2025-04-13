package com.example.moviesappxml.common.ui.models

import android.content.res.Resources
import com.example.moviesappxml.R

sealed class ErrorModel {
    data class RequestError(val errorMessage: String) : ErrorModel()
    data object InternetConnectionError : ErrorModel()
    data object UnknownError : ErrorModel()
}

fun ErrorModel.getUserFriendlyErrorMessage(resources: Resources): String {
    return when (this) {
        ErrorModel.InternetConnectionError -> resources.getString(R.string.check_your_internet_connection)
        is ErrorModel.RequestError -> this.errorMessage
        ErrorModel.UnknownError -> resources.getString(R.string.something_went_wrong_please_try_again)
    }
}
