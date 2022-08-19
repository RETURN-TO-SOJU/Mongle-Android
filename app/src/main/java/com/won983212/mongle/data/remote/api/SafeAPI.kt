package com.won983212.mongle.common.util

import android.util.Log
import com.won983212.mongle.data.remote.api.MESSAGE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

enum class RequestErrorType {
    UNKNOWN, NETWORK, HTTP
}

interface RequestLifecycleCallback {
    fun onStart()
    fun onComplete()
    fun onError(requestErrorType: RequestErrorType, msg: String)
}

suspend inline fun <T> safeApiCall(
    callback: RequestLifecycleCallback,
    crossinline callFunction: suspend () -> T
): T? {
    return try {
        withContext(Dispatchers.IO) {
            callback.onStart()
            val result = callFunction.invoke()
            callback.onComplete()
            result
        }
    } catch (e: Exception) {
        withContext(Dispatchers.Main) {
            e.printStackTrace()
            Log.e("safeApiCall", "Call error: ${e.localizedMessage}", e.cause)
            when (e) {
                is HttpException -> {
                    val body = e.response()?.errorBody()
                    callback.onError(RequestErrorType.HTTP, getErrorMessage(body))
                }
                is IOException -> callback.onError(
                    RequestErrorType.NETWORK,
                    "서버와 연결되지 않습니다."
                )
                else -> callback.onError(RequestErrorType.UNKNOWN, "처리되지 않은 오류입니다.")
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
            else -> "처리되지 않은 오류입니다. (no message)"
        }
    } catch (e: Exception) {
        Log.e(
            "safeApiCall",
            "Parse '${MESSAGE_KEY}' from error message: ${e.localizedMessage}",
            e.cause
        )
        "처리되지 않은 오류입니다."
    }
}