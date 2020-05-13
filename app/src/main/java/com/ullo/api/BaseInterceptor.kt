package com.ullo.api

import android.app.Application
import com.ullo.utils.Session
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class BaseInterceptor(internal var session: Session, internal var application: Application) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("Accept", "application/json")
        if (session.getAppUserToken().isNotEmpty()) {
            builder.addHeader("Authorization", "Bearer " + session.getAppUserToken())
        }
        return chain.proceed(builder.build())
    }
}
