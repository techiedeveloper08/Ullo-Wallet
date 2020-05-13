package com.ullo.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferenceHelper {

    fun <T> getStringFromObject(value: T): String {
        return Gson().toJson(value)
    }

    fun <T> getObjectFromString(str: String, typeTokenOfT: TypeToken<T>): T {
        return Gson().fromJson(str, typeTokenOfT.type)
    }
}