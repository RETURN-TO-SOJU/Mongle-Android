package com.rtsoju.mongle.data.source.remote.api

import android.util.Log
import com.rtsoju.mongle.debug.mock.MockingHttpException
import com.rtsoju.mongle.exception.ApiCallException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


const val MESSAGE_KEY = "message"

suspend inline fun <T> safeApiCall(
    crossinline callFunction: suspend () -> T
): Result<T> {
    return try {
        withContext(Dispatchers.IO) {
            Result.success(callFunction.invoke())
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("safeApiCall", "Call error: ${e.localizedMessage}", e.cause)
        val message = when (e) {
            is HttpException -> getErrorMessage(e.response()?.errorBody())
            is MockingHttpException -> e.message ?: "No message"
            is IOException -> "서버와 연결되지 않습니다."
            else -> "오류가 발생했습니다."
        }
        Result.failure(ApiCallException(message, e))
    }
}

fun getErrorMessage(responseBody: ResponseBody?): String {
    try {
        if (responseBody == null) {
            return "오류가 발생했습니다. (no message)"
        }

        val jsonObject = JSONObject(responseBody.string())
        if (jsonObject.has(MESSAGE_KEY)) {
            return jsonObject.getString(MESSAGE_KEY)
        }

        return "오류가 발생했습니다. (no message)"
    } catch (e: Exception) {
        Log.e(
            "safeApiCall",
            "Parse '$MESSAGE_KEY' from error message: ${e.localizedMessage}",
            e.cause
        )
        return "오류가 발생했습니다."
    }
}