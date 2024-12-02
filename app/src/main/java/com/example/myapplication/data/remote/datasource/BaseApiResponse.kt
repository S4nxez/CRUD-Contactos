package com.example.myapplication.data.remote.datasource

import com.example.myapplication.R
import com.example.myapplication.data.remote.NetworkResult
import com.example.myapplication.ui.utils.StringProvider
import retrofit2.Response
import timber.log.Timber

abstract class BaseApiResponse(private val stringProvider: StringProvider) {
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            Timber.e(e.message, e)
            return error(e.message ?: e.toString())
        }
    }

    suspend fun <T> safeApiCallNoBody(apiCall: suspend () -> Response<T>): NetworkResult<Boolean> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                return NetworkResult.Success(true)
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error(
            stringProvider.getString(R.string.E_API_CALL) + errorMessage)
}