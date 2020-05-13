package com.ullo.ui.contact

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.BuildConfig
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityContactBinding
import com.ullo.utils.Validation
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class ContactActivity : BaseActivity<ActivityContactBinding, ContactViewModel>(), ContactNavigator {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ContactActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: ContactViewModel
        get() = ViewModelProviders.of(this, factory).get(ContactViewModel::class.java)

    private var mActivityContactBinding: ActivityContactBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityContactBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        mActivityContactBinding!!.toolbar.setBackButton(true)
        mActivityContactBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })

        viewModel.getSession().getAppUser()?.run {
            mActivityContactBinding!!.etFullName.setText(userData.fullName)
            mActivityContactBinding!!.etEmail.setText(userData.email)
        }
    }

    override fun onSendButtonHandle() {
        if (isValid()) {
            val loginReq = JsonObject()
            loginReq.addProperty("name", mActivityContactBinding!!.etFullName.text.toString())
            loginReq.addProperty("email", mActivityContactBinding!!.etEmail.text.toString())
            loginReq.addProperty("subject", mActivityContactBinding!!.etSubject.text.toString())
            loginReq.addProperty("message", mActivityContactBinding!!.etMessage.text.toString())
            loginReq.addProperty("user_type", BuildConfig.USER_TYPE)
            viewModel.userContactUs(loginReq)
        }
    }

    private fun isValid(): Boolean {
        var bEmail = true
        var bFullName = true
        var bSubject = true
        var bMessage = true
        val firstName = mActivityContactBinding!!.etFullName.text.toString()
        val email = mActivityContactBinding!!.etEmail.text.toString()
        val subject = mActivityContactBinding!!.etSubject.text.toString()
        val message = mActivityContactBinding!!.etMessage.text.toString()

        if (!Validation.isValidName(firstName)) {
            mActivityContactBinding!!.tIFullName.isErrorEnabled = true
            mActivityContactBinding!!.tIFullName.error = "The entered name is not correct."
            bFullName = false
        } else {
            mActivityContactBinding!!.tIFullName.isErrorEnabled = false
        }

        if (!Validation.isValidEmail(email)) {
            mActivityContactBinding!!.tIEmail.isErrorEnabled = true
            mActivityContactBinding!!.tIEmail.error = "The entered Email is not correct."
            bEmail = false
        } else {
            mActivityContactBinding!!.tIEmail.isErrorEnabled = false
        }

        if (!Validation.isValidName(subject)) {
            mActivityContactBinding!!.tISubject.isErrorEnabled = true
            mActivityContactBinding!!.tISubject.error = "The entered subject is not correct."
            bSubject = false
        } else {
            mActivityContactBinding!!.tISubject.isErrorEnabled = false
        }

        if (!Validation.isValidName(message)) {
            mActivityContactBinding!!.tIMessage.isErrorEnabled = true
            mActivityContactBinding!!.tIMessage.error = "The entered subject is not correct."
            bMessage = false
        }else{
            mActivityContactBinding!!.tIMessage.isErrorEnabled = false
        }

        return bEmail && bFullName && bSubject && bMessage
    }

    override fun onContactUsSuccessfully() {
        handleError("Query submitted successfully")
    }
}