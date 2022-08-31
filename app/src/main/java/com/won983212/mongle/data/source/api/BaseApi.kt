package com.won983212.mongle.data.source.api

import android.util.Log
import com.won983212.mongle.debug.mock.MockingHttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException


const val MESSAGE_KEY = "message"

enum class RequestErrorType {
    UNKNOWN, NETWORK, HTTP
}

interface RequestLifecycleCallback {
    fun onStart()
    fun onComplete()
    fun onError(requestErrorType: RequestErrorType, msg: String)
}

object EmptyRequestLifecycleCallback : RequestLifecycleCallback {
    override fun onStart() {
    }

    override fun onComplete() {
    }

    override fun onError(requestErrorType: RequestErrorType, msg: String) {
    }
}

// TODO Error Handling방식을 개선할 필요가 있다.
// 나중에 Coroutine flow를 사용하든, exception을 사용하든 해야함
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
                is MockingHttpException -> {
                    callback.onError(RequestErrorType.HTTP, e.message ?: "No message")
                }
                is IOException -> callback.onError(
                    RequestErrorType.NETWORK,
                    "서버와 연결되지 않습니다."
                )
                else -> callback.onError(RequestErrorType.UNKNOWN, "오류가 발생했습니다.")
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
            else -> "오류가 발생했습니다. (no message)"
        }
    } catch (e: Exception) {
        Log.e(
            "safeApiCall",
            "Parse '${MESSAGE_KEY}' from error message: ${e.localizedMessage}",
            e.cause
        )
        "오류가 발생했습니다."
    }
}