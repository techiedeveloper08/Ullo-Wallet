package com.ullo.ui.send_money

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.R
import com.ullo.api.response.contact.Contact
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivitySendMoneyBinding
import com.ullo.extensions.disable
import com.ullo.extensions.visible
import com.ullo.ui.setting.SettingActivity
import com.ullo.utils.Constant
import com.ullo.utils.Validation
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class SendMoneyActivity : BaseActivity<ActivitySendMoneyBinding, SendMoneyViewModel>(), SendMoneyNavigator {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SendMoneyActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: SendMoneyViewModel
        get() = ViewModelProviders.of(this, factory).get(SendMoneyViewModel::class.java)

    private var mActivitySendMoneyBinding: ActivitySendMoneyBinding? = null

    private lateinit var contact: Contact

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_send_money

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivitySendMoneyBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        intent?.run {
            contact = getParcelableExtra(Constant.PARAM_CONTACT) as Contact
        }

        mActivitySendMoneyBinding!!.toolbar.setBackButton(true)
        mActivitySendMoneyBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })

        mActivitySendMoneyBinding!!.toolbar.setRightButtonListener(listener = View.OnClickListener {
            SettingActivity.newIntent(this).apply {
                startActivity(this)
            }
        })

        contact?.run {
            mActivitySendMoneyBinding!!.txtContactInfo.text = getString(R.string.paying_money_to_sophia_fields, contact.fullName, contact.phoneNumber)
        }

        mActivitySendMoneyBinding!!.etAmount.requestFocus()
        mActivitySendMoneyBinding!!.btnUpdate.setOnClickListener {
            if (mActivitySendMoneyBinding!!.btnUpdate.text.toString() == "Send") {
                if (isValid()) {
                    val req = JsonObject()
                    req.addProperty("amount", mActivitySendMoneyBinding!!.etAmount.text.toString())
                    req.addProperty("phone_number", contact.phoneNumber)
                    viewModel.userSendMoney(req)
                }
            } else {
                finish()
            }
        }
    }

    private fun isValid(): Boolean {
        var bAmount = true
        val amount = mActivitySendMoneyBinding!!.etAmount.text.toString()

        if (!Validation.isValidNumber(amount)) {
            //  mActivitySendMoneyBinding!!.etAmount.isErrorEnabled = true
            mActivitySendMoneyBinding!!.etAmount.error = "The entered amount is not correct."
            bAmount = false
        } else {
            //  mActivitySendMoneyBinding!!.etAmount.isErrorEnabled = false
        }

        return bAmount
    }

    override fun onSendMoneySuccess() {
        mActivitySendMoneyBinding!!.imgSuccess.visible()
        mActivitySendMoneyBinding!!.btnUpdate.text = "Close"
        mActivitySendMoneyBinding!!.toolbar.setToolbarTitle("Success")
        mActivitySendMoneyBinding!!.txtContactInfo.text = "Payment transfer to ${contact.fullName}"
        mActivitySendMoneyBinding!!.etAmount.disable()
    }
}