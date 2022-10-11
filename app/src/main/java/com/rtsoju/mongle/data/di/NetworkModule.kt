package com.rtsoju.mongle.data.di

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.rtsoju.mongle.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        authenticationInterceptor: AuthenticationInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authenticationInterceptor)
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java,
                JsonDeserializer { json, _, _ ->
                    var stringData = json.asString
                    if (stringData.endsWith("Z")) {
                        stringData = stringData.substring(0, stringData.length - 1)
                    }
                    LocalDateTime.parse(stringData)
                })
            .registerTypeAdapter(LocalDate::class.java,
                JsonDeserializer { json, _, _ ->
                    LocalDate.parse(json.asString)
                })
            .registerTypeAdapter(LocalTime::class.java,
                JsonDeserializer { json, _, _ ->
                    LocalTime.parse(json.asString)
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
        return GsonConverterFactory.create(gson)
    }
}