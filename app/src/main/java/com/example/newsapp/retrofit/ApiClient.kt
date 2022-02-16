package com.letmelisten.app.retrofit

import android.app.Activity
import com.google.gson.GsonBuilder
import com.letmelisten.app.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient {
    public var mRetrofit: Retrofit? = null
    public var mInstance: ApiClient? = null


    fun getApiClientString(mActivity: Activity?): Retrofit? {
        if (mRetrofit == null) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val okHttpClient =
                OkHttpClient.Builder().addInterceptor(interceptor)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build()
            mRetrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(ScalarsConverterFactory.create())
//                .client(getUnsafeOkHttpClient11(mActivity).build())
                .build()
        }
        return mRetrofit
    }

    @Synchronized
    fun getInstance(): ApiClient? {
        if (mInstance == null) {
            mInstance = ApiClient()
        }
        return mInstance
    }

    fun getApiClient(): Retrofit? {

        var interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor)
            .connectTimeout(7, TimeUnit.MINUTES)
            .readTimeout(7, TimeUnit.MINUTES)
            .writeTimeout(7, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("Content-Type", "application/json")

                val request = requestBuilder.build()
                chain.proceed(request)
            }.build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        mRetrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return mRetrofit;
    }
}