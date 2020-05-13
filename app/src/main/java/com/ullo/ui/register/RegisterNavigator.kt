package com.ullo.ui.register

import com.ullo.api.response.AppUser
import com.ullo.base.BaseNavigator


interface RegisterNavigator : BaseNavigator {
    fun onLoginScreen()
    fun onCheckValidation()
    fun onRegisterSuccessful(appUser: AppUser)
    fun onOtpVerificationSuccessful()
}