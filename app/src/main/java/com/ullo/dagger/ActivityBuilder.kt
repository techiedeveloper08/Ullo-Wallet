package com.ullo.dagger

import com.ullo.ui.balance_history.BalanceHistoryActivity
import com.ullo.ui.balance_history.BalanceHistoryActivityModule
import com.ullo.ui.change_password.ChangePasswordActivity
import com.ullo.ui.choose_contact.ChooseContactActivity
import com.ullo.ui.choose_contact.ChooseContactActivityModule
import com.ullo.ui.contact.ContactActivity
import com.ullo.ui.forgot_password.ForgotPasswordActivity
import com.ullo.ui.landing.LandingActivity
import com.ullo.ui.login.LoginActivity
import com.ullo.ui.main.MainActivity
import com.ullo.ui.manage_money.ManageMoneyActivity
import com.ullo.ui.my_qr_code.MyQrCodeActivity
import com.ullo.ui.notification.NotificationActivity
import com.ullo.ui.notification.NotificationActivityModule
import com.ullo.ui.profile.ProfileActivity
import com.ullo.ui.register.RegisterActivity
import com.ullo.ui.scan_qr_code.ScanQrCodeActivity
import com.ullo.ui.send_money.SendMoneyActivity
import com.ullo.ui.setting.SettingActivity
import com.ullo.ui.splash.SplashActivity
import com.ullo.ui.tutorial.TutorialActivity
import com.ullo.ui.tutorial.TutorialActivityModule
import com.ullo.ui.webview.WebviewActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun bindSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun bindLandingActivity(): LandingActivity

    @ContributesAndroidInjector
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun bindContactActivity(): ContactActivity

    @ContributesAndroidInjector
    internal abstract fun bindProfileActivity(): ProfileActivity

    @ContributesAndroidInjector
    internal abstract fun bindWebviewActivity(): WebviewActivity

    @ContributesAndroidInjector
    internal abstract fun bindChangePasswordActivity(): ChangePasswordActivity

    @ContributesAndroidInjector(modules = [ChooseContactActivityModule::class])
    internal abstract fun bindChooseContactActivity(): ChooseContactActivity

    @ContributesAndroidInjector
    internal abstract fun bindForgotPasswordActivity(): ForgotPasswordActivity

    @ContributesAndroidInjector(modules = [TutorialActivityModule::class])
    internal abstract fun bindTutorialActivity(): TutorialActivity

    @ContributesAndroidInjector
    internal abstract fun bindSettingActivity(): SettingActivity

    @ContributesAndroidInjector
    internal abstract fun bindMyQrCodeActivity(): MyQrCodeActivity

    @ContributesAndroidInjector
    internal abstract fun bindSendMoneyActivity(): SendMoneyActivity

    @ContributesAndroidInjector
    internal abstract fun bindScanQrCodeActivity(): ScanQrCodeActivity

    @ContributesAndroidInjector
    internal abstract fun bindManageMoneyActivity(): ManageMoneyActivity

    @ContributesAndroidInjector(modules = [BalanceHistoryActivityModule::class])
    internal abstract fun bindBalanceHistoryActivity(): BalanceHistoryActivity

    @ContributesAndroidInjector(modules = [NotificationActivityModule::class])
    internal abstract fun bindNotificationActivity(): NotificationActivity
}