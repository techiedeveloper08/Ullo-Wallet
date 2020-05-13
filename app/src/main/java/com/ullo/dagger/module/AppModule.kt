package com.ullo.dagger.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.ullo.api.service.UlloService
import com.ullo.db.AppDatabase
import com.ullo.db.AppDbHelper
import com.ullo.db.DataManager
import com.ullo.utils.Constant
import com.ullo.utils.Session
import com.ullo.utils.ViewModelProviderFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSession(context: Context): Session {
        return Session(context)
    }

    @Provides
    @Singleton
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, Constant.DB_NAME).allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    fun provideDataManager(appDbHelper: AppDbHelper): DataManager {
        return DataManager(appDbHelper)
    }

    @Provides
    @Singleton
    fun provideDbHelper(appDatabase: AppDatabase): AppDbHelper {
        return AppDbHelper(appDatabase)
    }

    @Provides
    @Singleton
    fun provideViewModelProviderFactory(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager): ViewModelProviderFactory {
        return ViewModelProviderFactory(application, ulloService, session, dataManager)
    }
}