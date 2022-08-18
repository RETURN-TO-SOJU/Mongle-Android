package com.won983212.mongle.common.util

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

enum class ErrorType {
    UNKNOWN, NETWORK, HTTP
}

interface NetworkErrorHandler {
    fun onNetworkError(errorType: ErrorType, msg: String = "")
}

class SafeAPI {
    suspend inline fun <T> safeApiCall(
        emitter: NetworkErrorHandler,
        crossinline callFunction: suspend () -> T
    ): T? {
        return try {
            withContext(Dispatchers.IO) { callFunction.invoke() }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                e.printStackTrace()
                Log.e("safeApiCall", "Call error: ${e.localizedMessage}", e.cause)
                when (e) {
                    is HttpException -> {
                        val body = e.response()?.errorBody()
                        emitter.onNetworkError(ErrorType.HTTP, getErrorMessage(body))
                    }
                    is IOException -> emitter.onNetworkError(ErrorType.NETWORK)
                    else -> emitter.onNetworkError(ErrorType.UNKNOWN)
                }
            }
            null
        }
    }

    fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            when {
                jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                else -> "Something wrong happened"
            }
        } catch (e: Exception) {
            "Something wrong happened"
        }
    }

    companion object {
        private const val MESSAGE_KEY = "message"
    }
}