package com.ullo.api.response

import com.google.gson.annotations.SerializedName

data class CmsData(@SerializedName("about_us")
                    val aboutUs: String,
                   @SerializedName("terms_and_condition")
                    val termsAndCondition: String,
                   @SerializedName("privacy")
                    val privacy: String)