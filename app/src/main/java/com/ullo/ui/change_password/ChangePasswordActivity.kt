package com.ullo.ui.change_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.BuildConfig
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityChangePasswordBinding
import com.ullo.utils.Validation
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject


class ChangePasswordActivity : BaseActivity<ActivityChangePasswordBinding, ChangePasswordViewModel>(), ChangePasswordNavigator {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ChangePasswordActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: ChangePasswordViewModel
        get() = ViewModelProviders.of(this, factory).get(ChangePasswordViewModel::class.java)

    private var mActivityChangePasswordBinding: ActivityChangePasswordBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_change_password

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityChangePasswordBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        val typeface = ResourcesCompat.getFont(this, R.font.quicksand_regular)
        mActivityChangePasswordBinding!!.etOldPassword.typeface = typeface
        mActivityChangePasswordBinding!!.etOldPassword.transformationMethod = PasswordTransformationMethod()
        mActivityChangePasswordBinding!!.etNewPassword.typeface = typeface
        mActivityChangePasswordBinding!!.etNewPassword.transformationMethod = PasswordTransformationMethod()
        mActivityChangePasswordBinding!!.etConfirmPassword.typeface = typeface
        mActivityChangePasswordBinding!!.etConfirmPassword.transformationMethod = PasswordTransformationMethod()

        mActivityChangePasswordBinding!!.toolbar.setBackButton(true)
        mActivityChangePasswordBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })
    }

    override fun onChangePasswordHandle() {
        if (isValid()) {
            val changePasswordReq = JsonObject()
            changePasswordReq.addProperty("old_password", mActivityChangePasswordBinding!!.etOldPassword.text.toString())
            changePasswordReq.addProperty("confirm_password", mActivityChangePasswordBinding!!.etNewPassword.text.toString())
            viewModel.changePassword(changePasswordReq)
        }
    }

    private fun isValid(): Boolean {
        var bOldPassword = true
        var bNewPassword = true
        var bConfirmPassword = true
        val oldPassword = mActivityChangePasswordBinding!!.etOldPassword.text.toString()
        val newPassword = mActivityChangePasswordBinding!!.etNewPassword.text.toString()
        val confirmPassword = mActivityChangePasswordBinding!!.etConfirmPassword.text.toString()

        if (!Validation.isValidPassword(oldPassword)) {
            mActivityChangePasswordBinding!!.tIOldPassword.isErrorEnabled = true
            mActivityChangePasswordBinding!!.tIOldPassword.error = "Password must be at least 8 characters"
            bOldPassword = false
        } else {
            mActivityChangePasswordBinding!!.tIOldPassword.isErrorEnabled = false
        }

        if (!Validation.isValidPassword(newPassword)) {
            mActivityChangePasswordBinding!!.tINewPassword.isErrorEnabled = true
            mActivityChangePasswordBinding!!.tINewPassword.error = "Password must be at least 8 characters"
            bNewPassword = false
        } else {
            mActivityChangePasswordBinding!!.tINewPassword.isErrorEnabled = false
        }

        if (!Validation.isValidPassword(confirmPassword)) {
            mActivityChangePasswordBinding!!.tIConfirmPassword.isErrorEnabled = true
            mActivityChangePasswordBinding!!.tIConfirmPassword.error = "Password must be at least 8 characters"
            bConfirmPassword = false
        } else {
            if (!Validation.isMatchPassword(newPassword, confirmPassword)) {
                mActivityChangePasswordBinding!!.tIConfirmPassword.isErrorEnabled = true
                mActivityChangePasswordBinding!!.tIConfirmPassword.error = "Password not match"
                bConfirmPassword = false
            } else {
                mActivityChangePasswordBinding!!.tIConfirmPassword.isErrorEnabled = false
            }
        }

        return bOldPassword && bNewPassword && bConfirmPassword
    }

    override fun onChangePasswordSuccess() {
        handleError("Password successfully updated")
    }
}