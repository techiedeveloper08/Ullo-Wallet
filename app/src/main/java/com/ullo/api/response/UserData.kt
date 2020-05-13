package com.ullo.api.response

import com.google.gson.annotations.SerializedName

data class UserData(@SerializedName("id")
                    val id: Int,
                    @SerializedName("full_name")
                    val fullName: String,
                    @SerializedName("email")
                    val email: String,
                    @SerializedName("image")
                    val image: String,
                    @SerializedName("country_code")
                    val countryCode: String,
                    @SerializedName("phone_number")
                    val phoneNumber: String,
                    @SerializedName("user_type")
                    val userType: String,
                    @SerializedName("qr_image")
                    val qrImage: String,
                    @SerializedName("is_active")
                    val isActive: String,
                    @SerializedName("phone_verified")
                    val phoneVerified: String,
                    @SerializedName("email_verified")
                    val emailVerified: String,
                    @SerializedName("is_profile_completed")
                    val isProfileCompleted: String,
                    @SerializedName("notification_enabled")
                    val notificationEnabled: String)