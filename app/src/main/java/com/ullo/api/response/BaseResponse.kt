package com.ullo.api.response

import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(@SerializedName("success")
                           val success: Int,
                           @SerializedName(value = "data", alternate = ["user"])
                           val data: T,
                           @SerializedName("error")
                           val error: JsonElement)