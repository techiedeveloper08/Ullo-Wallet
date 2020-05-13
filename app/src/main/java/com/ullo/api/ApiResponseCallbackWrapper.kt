package com.ullo.api

import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.net.UnknownHostException
import javax.net.ssl.SSLException

abstract class ApiResponseCallbackWrapper<T> : DisposableObserver<T>() {

    protected abstract fun onSuccess(response: T)

    protected abstract fun onInternetConnectionError()

    protected abstract fun onFailure(error: String)

    override fun onNext(value: T) {
        if (value is Response<*>) {
            val response = value as Response<*>
            when {
                response.code() >= 500 -> {
                    onFailure("Something went wrong. Please try again")
                }
                else -> onSuccess(value)
            }
        } else{
            onSuccess(value)
        }

    }

    override fun onError(error: Throwable) {
        if (error is ConnectivityInterceptor.NoConnectivityException || error is UnknownHostException || error is SSLException) {
            onInternetConnectionError()
        } else {
            onFailure(error.localizedMessage)
        }
    }

    private fun getErrorMessage(responseBody: ResponseBody?): String {
        return try {
            val jsonObject = JSONObject(responseBody!!.string())
            jsonObject.getString("message")
        } catch (e: Exception) {
            e.message!!
        }
    }

    override fun onComplete() {

    }
}
