package com.ullo.ui.login

import com.ullo.base.BaseNavigator


interface LoginNavigator : BaseNavigator {
    fun onLoginHandle()
    fun onMainScreen()
    fun onForgotPasswordHandle()
    fun onSignUpHandle()
}