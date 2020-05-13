package com.ullo.ui.tutorial

import android.content.Context
import com.linderaredux.adapter.CustomPagerAdapter
import com.ullo.adapter.ContactAdapter
import dagger.Module
import dagger.Provides

@Module
class TutorialActivityModule {

    @Provides
    fun provideCustomPagerAdapter(context: Context): CustomPagerAdapter {
        return CustomPagerAdapter(context)
    }
}