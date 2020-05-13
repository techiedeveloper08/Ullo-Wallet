package com.ullo.base

interface BaseNavigator {
    fun handleError(error: String)
    fun onInternetConnectionError()
}