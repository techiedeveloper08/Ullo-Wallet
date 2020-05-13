package com.ullo.ui.notification

import android.content.Context
import com.ullo.adapter.BalanceHistoryAdapter
import com.ullo.adapter.ContactAdapter
import com.ullo.adapter.NotificationAdapter
import dagger.Module
import dagger.Provides

@Module
class NotificationActivityModule {

    @Provides
    fun provideNotificationAdapter(context: Context): NotificationAdapter {
        return NotificationAdapter(context)
    }
}