package com.won983212.mongle

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.TypeAdapter
import com.google.gson.annotations.SerializedName
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.won983212.mongle.data.model.Emotion
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Ignore
import org.junit.Test
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit


@Ignore("반드시 필요한 테스트는 아님. 임시방편 테스트")
class RetrofitTest {

    data class TestResponse(
        @SerializedName("name")
        val name: String,
        @SerializedName("diary")
        val diary: String,
        @SerializedName("writtenAt")
        val writtenAt: LocalDateTime,
        @SerializedName("emotion")
        val emotion: Emotion
    )

    interface TestApi {
        @GET("api")
        @NoAuthorization
        suspend fun testapi(): TestResponse
    }

    annotation class NoAuthorization

    @Test
    fun test_api() {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val authorizationInterceptor = object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request = chain.request()
                val noAuthAnnotation = request.tag(Invocation::class.java)
                    ?.method()
                    ?.getAnnotation(NoAuthorization::class.java)

                println("URL: ${request.url}")
                println(
                    "NoAuthAnnotation: ${noAuthAnnotation.toString()}"
                )

                if (noAuthAnnotation == null) {
                    val token = "12345"
                    request = request.newBuilder()
                        .addHeader("Authorization", "Bearer $token")
                        .build()
                }

                return chain.proceed(request)
            }
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authorizationInterceptor)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder().registerTypeAdapter(
            LocalDateTime::class.java,
            JsonDeserializer { json, typeOfT, context ->
                LocalDateTime.parse(
                    json.asString,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
                )
            })
            .registerTypeAdapter(
                LocalDate::class.java,
                JsonDeserializer { json, typeOfT, context ->
                    LocalDate.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    )
                })
            .registerTypeAdapter(
                LocalTime::class.java,
                JsonDeserializer { json, typeOfT, context ->
                    LocalTime.parse(
                        json.asString,
                        DateTimeFormatter.ofPattern("HH:mm:ss")
                    )
                })
            .registerTypeAdapter(
                String::class.java,
                object : TypeAdapter<String>() {
                    override fun write(writer: JsonWriter, value: String?) {
                        writer.value(value)
                    }

                    override fun read(reader: JsonReader): String {
                        if (reader.peek() == JsonToken.NULL) {
                            reader.nextNull()
                            return ""
                        }
                        return reader.nextString()
                    }
                })
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://b1d69888-8e3e-4539-a733-ec7c692db06a.mock.pstmn.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val api = retrofit.create(TestApi::class.java)

        runBlocking {
            println(api.testapi())
        }
    }
}