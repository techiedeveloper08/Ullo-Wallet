package com.ullo.ui.forgot_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.BuildConfig
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityForgotPasswordBinding
import com.ullo.utils.Validation
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class ForgotPasswordActivity : BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(), ForgotPasswordNavigator {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ForgotPasswordActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: ForgotPasswordViewModel
        get() = ViewModelProviders.of(this, factory).get(ForgotPasswordViewModel::class.java)

    private var mActivityForgotPasswordBinding: ActivityForgotPasswordBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_forgot_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityForgotPasswordBinding = getViewDataBinding()
        viewModel.setNavigator(this)
    }

    override fun onLoginHandle() {
        finish()
    }

    override fun onForgotPasswordHandle() {
        if (isValid()) {
            val loginReq = JsonObject()
            loginReq.addProperty("email", mActivityForgotPasswordBinding!!.etEmail.text.toString())
            loginReq.addProperty("user_type", BuildConfig.USER_TYPE)
            loginReq.addProperty("device_type", BuildConfig.DEVICE_TYPE)
            loginReq.addProperty("device_id", viewModel.getSession().getAppDeviceId())
            viewModel.forgotPassword(loginReq)
        }
    }

    private fun isValid(): Boolean {
        var bEmail = true
        val email = mActivityForgotPasswordBinding!!.etEmail.text.toString()

        if (!Validation.isValidEmail(email)) {
            mActivityForgotPasswordBinding!!.tIEmail.isErrorEnabled = true
            mActivityForgotPasswordBinding!!.tIEmail.error = "The entered Email is not correct."
            bEmail = false
        } else {
            mActivityForgotPasswordBinding!!.tIEmail.isErrorEnabled = false
        }

        return bEmail
    }

    override fun onForgotPasswordSuccess() {
        handleError("Check your email for reset password link")
    }
}