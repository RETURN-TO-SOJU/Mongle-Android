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
    fun onNetworkError(errorType: ErrorType, msg: String)
}

class SafeAPI {
    companion object {
        const val MESSAGE_KEY = "message"
    }
}

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
                is IOException -> emitter.onNetworkError(ErrorType.NETWORK, "서버와 연결되지 않습니다.")
                else -> emitter.onNetworkError(ErrorType.UNKNOWN, "처리되지 않은 오류입니다.")
            }
        }
        null
    }
}

fun getErrorMessage(responseBody: ResponseBody?): String {
    return try {
        val jsonObject = JSONObject(responseBody!!.string())
        when {
            jsonObject.has(SafeAPI.MESSAGE_KEY) -> jsonObject.getString(SafeAPI.MESSAGE_KEY)
            else -> "Something wrong happened"
        }
    } catch (e: Exception) {
        "Something wrong happened"
    }
}