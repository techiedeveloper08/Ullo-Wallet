package com.ullo.api.response

import com.google.gson.annotations.SerializedName

data class AppUser(@SerializedName("user_data")
                   val userData: UserData,
                   @SerializedName("token")
                   val token: Token)