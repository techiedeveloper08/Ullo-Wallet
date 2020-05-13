package com.ullo.ui.main

import com.google.gson.JsonElement
import com.ullo.base.BaseNavigator

interface MainNavigator : BaseNavigator {
    fun onMenuHandle()
    fun onSendMoneyHandle()
    fun onReceiveMoneyHandle()
    fun onNotificationHandle()
    fun onAccountInfoSuccess(data: JsonElement)
    fun onMangeMoneyHandle()
}