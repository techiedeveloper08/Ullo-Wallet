package com.ullo.utils

import android.app.Application
import com.google.gson.GsonBuilder
import com.ullo.BuildConfig
import com.ullo.api.BaseInterceptor
import com.ullo.api.ConnectivityInterceptor
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    fun getRetrofit(session: Session, application: Application, API: String): Retrofit {
        val gson = GsonBuilder()
                .setLenient()
                .create()

        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        httpClient.interceptors().add(BaseInterceptor(session, application))
        httpClient.interceptors().add(ConnectivityInterceptor())
        httpClient.connectTimeout(1, TimeUnit.MINUTES).writeTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)

        return Retrofit.Builder()
                .baseUrl(API)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }
}
