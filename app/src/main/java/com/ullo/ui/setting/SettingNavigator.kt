package com.ullo.ui.setting

import com.ullo.base.BaseNavigator


interface SettingNavigator : BaseNavigator {
    fun onMyProfileHandle()
    fun onMyQRCodeHandle()
    fun onAddCardHandle()
    fun onChangePasswordHandle()
    fun onTermsConditionHandle()
    fun onPrivacyPolicesHandle()
    fun onSignOutHandle()
    fun onContactUsHandle()
    fun onAboutUsHandle()
    fun onNotificationSettingSuccess()
}