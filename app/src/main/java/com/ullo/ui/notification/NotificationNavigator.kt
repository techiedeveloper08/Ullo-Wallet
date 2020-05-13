package com.ullo.ui.notification

import com.ullo.api.response.notification.NotificationData
import com.ullo.base.BaseNavigator

interface NotificationNavigator : BaseNavigator {
    fun onNotificationSuccessfully(data: NotificationData)
}