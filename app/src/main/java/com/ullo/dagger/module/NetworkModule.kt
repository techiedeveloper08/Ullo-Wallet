package com.ullo.dagger.module

import android.app.Application
import com.ullo.BuildConfig
import com.ullo.api.service.UlloApi
import com.ullo.api.service.UlloService
import com.ullo.utils.RetrofitFactory
import com.ullo.utils.Session
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun providesUlloService(session: Session, application: Application): UlloService {
        return UlloService(RetrofitFactory.getRetrofit(session, application, BuildConfig.SERVICE_ENDPOINT).create(UlloApi::class.java))
    }
}