package com.ullo.ui.setting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivitySettingBinding
import com.ullo.ui.change_password.ChangePasswordActivity
import com.ullo.ui.contact.ContactActivity
import com.ullo.ui.landing.LandingActivity
import com.ullo.ui.my_qr_code.MyQrCodeActivity
import com.ullo.ui.profile.ProfileActivity
import com.ullo.ui.webview.WebviewActivity
import com.ullo.utils.Constant
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class SettingActivity : BaseActivity<ActivitySettingBinding, SettingViewModel>(), SettingNavigator {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: SettingViewModel
        get() = ViewModelProviders.of(this, factory).get(SettingViewModel::class.java)

    private var mActivitySettingBinding: ActivitySettingBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivitySettingBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        mActivitySettingBinding!!.toolbar.setBackButton(true)
        mActivitySettingBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })

        viewModel.run {
            mActivitySettingBinding!!.swSyncContact.isChecked = getSession().getSyncContact()
            mActivitySettingBinding!!.swPushNotification.isChecked = getSession().getPushNotification()
            mActivitySettingBinding!!.swEmailNotification.isChecked = getSession().getEmailNotification()
        }

        mActivitySettingBinding!!.swSyncContact.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.getSession().setSyncContact(isChecked)
        }
        mActivitySettingBinding!!.swPushNotification.setOnCheckedChangeListener { buttonView, isChecked ->
            val registerReq = JsonObject()
            registerReq.addProperty("is_notification", if (isChecked) 1 else 0)
            viewModel.userNotificationSetting(registerReq)
        }
    }

    override fun onMyProfileHandle() {
        ProfileActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onMyQRCodeHandle() {
        MyQrCodeActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onAddCardHandle() {

    }

    override fun onChangePasswordHandle() {
        ChangePasswordActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onTermsConditionHandle() {
        viewModel.getSession().getAppCmsData()?.run {
            WebviewActivity.newIntent(this@SettingActivity).apply {
                putExtra(Constant.PARAM_WEBVIEW_TITLE, "Terms & Conditions")
                putExtra(Constant.PARAM_WEBVIEW_URL, termsAndCondition)
                startActivity(this)
            }
        }
    }

    override fun onPrivacyPolicesHandle() {
        viewModel.getSession().getAppCmsData()?.run {
            WebviewActivity.newIntent(this@SettingActivity).apply {
                putExtra(Constant.PARAM_WEBVIEW_TITLE, "Privacy Policy")
                putExtra(Constant.PARAM_WEBVIEW_URL, privacy)
                startActivity(this)
            }

        }
    }

    override fun onContactUsHandle() {
        ContactActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onAboutUsHandle() {
        viewModel.getSession().getAppCmsData()?.run {
            WebviewActivity.newIntent(this@SettingActivity).apply {
                putExtra(Constant.PARAM_WEBVIEW_TITLE, "About Us")
                putExtra(Constant.PARAM_WEBVIEW_URL, aboutUs)
                startActivity(this)
            }
        }
    }

    override fun onSignOutHandle() {
        finishAffinity()
        LandingActivity.newIntent(this).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or  Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(this)
        }
    }

    override fun onNotificationSettingSuccess() {
        viewModel.getSession().setPushNotification(mActivitySettingBinding!!.swPushNotification.isChecked)
    }
}