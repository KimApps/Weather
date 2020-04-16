package com.apps.kim.weather.network.api

import com.apps.kim.weather.app.*
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.apps.kim.weather.tools.utils.PrefProvider
import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
Created by KIM on 04.11.2019
 **/

open class Api {
    val api: ApplicationApi
    private var request: Request

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .build()
        request = retrofit.create(Request::class.java)
        api = ApplicationApi(request)
    }

    companion object {
        val instance = Api()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().run {
            //for add header for all request
            addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                requestBuilder.method(original.method(), original.body())
                return@addInterceptor chain.proceed(addHeader(requestBuilder))
            }
            build()
        }
    }


    private fun addHeader(requestBuilder: okhttp3.Request.Builder): okhttp3.Request {
        requestBuilder.removeHeader(HEADER_AUTHORIZATION)
        if (PrefProvider.token.isNotEmpty()) {
            with(PrefProvider) {
                requestBuilder.addHeader(HEADER_AUTHORIZATION, token)
            }
        }
        requestBuilder.addHeader(
            CONTENT_TYPE,
            CONTENT_TYPE_VALUE
        )
        return requestBuilder.build()
    }

    private fun responseCount(response: Response): Int {
        var result = DEFAULT_RESPONSE_COUNT
        var lResponse: Response? = response
        while ({ lResponse = lResponse?.priorResponse(); lResponse }() != null) {
            result++
        }
        return result
    }

    private fun <T> unauthorizedHandler() = FlowableTransformer<T, T> {
        it.onErrorResumeNext { throwable: Throwable ->
            if (throwable !is HttpException) {
                // return@onErrorResumeNext Flowable.error(throwable)
                return@onErrorResumeNext doLogoutFlowable<T>()
            }
            return@onErrorResumeNext doLogoutFlowable<T>()
        }
    }

    private fun <T> doLogoutFlowable(): Flowable<T> {
        App.instance.onLogout()
        return Flowable.error(IllegalAccessException("Wrong credentials"))
    }
}