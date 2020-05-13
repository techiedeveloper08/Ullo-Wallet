package com.ullo

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDex
import com.github.tamir7.contacts.Contacts
import com.ullo.base.BaseActivity
import com.ullo.dagger.DaggerAppComponent
import com.ullo.utils.RxBus
import com.ullo.utils.ShowLoadingOverlayDialog
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector, Application.ActivityLifecycleCallbacks {

    @set:Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    lateinit var currentActivity: AppCompatActivity

    private var bus: RxBus? = null

    init {
        instance = this
    }

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Contacts.initialize(this)
        MultiDex.install(this)

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this)

        registerActivityLifecycleCallbacks(this)
    }

    fun rxBus(): RxBus {
        if (bus == null) {
            bus = RxBus()
        }
        return bus!!
    }

    fun showLoadingOverlayDialog(msg: String) {
        rxBus().send(ShowLoadingOverlayDialog(msg))
    }

    fun hideLoadingOverlayDialog() {
        rxBus().send(BaseActivity.HIDE_LOADING_OVERLAY_DIALOG)
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return activityDispatchingAndroidInjector
    }

    override fun onActivityPaused(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
        currentActivity = activity as AppCompatActivity
    }

    override fun onActivityStarted(activity: Activity?) {
        currentActivity = activity as AppCompatActivity
    }

    override fun onActivityDestroyed(activity: Activity?) {

    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }

    override fun onActivityStopped(activity: Activity?) {

    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        currentActivity = activity as AppCompatActivity
    }
}