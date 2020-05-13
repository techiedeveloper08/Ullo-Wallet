package com.ullo.ui.splash

import com.ullo.base.BaseNavigator

interface SplashNavigator : BaseNavigator {
    fun onLandingScreen()
    fun onMainScreen()
    fun onTutorialScreen()
}