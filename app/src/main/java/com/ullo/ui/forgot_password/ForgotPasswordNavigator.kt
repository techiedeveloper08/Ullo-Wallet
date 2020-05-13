package com.ullo.ui.forgot_password

import com.ullo.base.BaseNavigator


interface ForgotPasswordNavigator : BaseNavigator {
    fun onForgotPasswordHandle()
    fun onForgotPasswordSuccess()
    fun onLoginHandle()
}