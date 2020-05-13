package com.ullo.ui.choose_contact

import android.content.Context
import com.ullo.adapter.ContactAdapter
import dagger.Module
import dagger.Provides

@Module
class ChooseContactActivityModule {

    @Provides
    fun provideContactAdapter(context: Context): ContactAdapter {
        return ContactAdapter(context)
    }
}