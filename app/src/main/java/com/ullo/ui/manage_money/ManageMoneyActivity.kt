package com.ullo.ui.manage_money

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.flutterwave.raveandroid.RaveConstants
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RavePayManager
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.R
import com.ullo.api.response.Payment
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityManageMoneyBinding
import com.ullo.extensions.disable
import com.ullo.extensions.visible
import com.ullo.ui.setting.SettingActivity
import com.ullo.utils.Validation
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject


class ManageMoneyActivity : BaseActivity<ActivityManageMoneyBinding, ManageMoneyViewModel>(), ManageMoneyNavigator {

    companion object {
        private const val READ_PHONE_STATE_PERMISSION_REQUEST_CODE = 76
        fun newIntent(context: Context): Intent {
            return Intent(context, ManageMoneyActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: ManageMoneyViewModel
        get() = ViewModelProviders.of(this, factory).get(ManageMoneyViewModel::class.java)

    private var mActivityManageMoneyBinding: ActivityManageMoneyBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_manage_money

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityManageMoneyBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        mActivityManageMoneyBinding!!.toolbar.setBackButton(true)
        mActivityManageMoneyBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })

        mActivityManageMoneyBinding!!.toolbar.setRightButtonListener(listener = View.OnClickListener {
            SettingActivity.newIntent(this).apply {
                startActivity(this)
            }
        })

        mActivityManageMoneyBinding!!.etAmount.requestFocus()
        mActivityManageMoneyBinding!!.btnUpdate.setOnClickListener {
            if (mActivityManageMoneyBinding!!.btnUpdate.text.toString() == "Add Money") {
                if (isValid()) {
                    val payableAmount = mActivityManageMoneyBinding!!.etAmount.text.toString().toDouble()
                    onPermissionCheck(payableAmount)
                }
            } else {
                finish()
            }
        }
    }

    private fun isValid(): Boolean {
        var bAmount = true
        val amount = mActivityManageMoneyBinding!!.etAmount.text.toString()

        if (!Validation.isValidNumber(amount)) {
            //  mActivitySendMoneyBinding!!.etAmount.isErrorEnabled = true
            mActivityManageMoneyBinding!!.etAmount.error = "The entered amount is not correct."
            bAmount = false
        } else {
            //  mActivitySendMoneyBinding!!.etAmount.isErrorEnabled = false
        }

        return bAmount
    }

    private fun onAddMoney(payableAmount: Double) {
        RavePayManager(this@ManageMoneyActivity).setAmount(payableAmount)
                .setCountry("GH")
                .setCurrency("GHS")
                .setEmail(viewModel.getSession().getAppUser().userData.email)
                .setfName(viewModel.getSession().getAppUser().userData.fullName)
                .setlName("")
                .setNarration("")
                .setPublicKey("FLWPUBK-93c0878ad323386aa1a37037835bd5b6-X")
                .setEncryptionKey("68ef6fe723eb90ae66ae1bc0")
                .setTxRef("" + System.currentTimeMillis()) //OrderId
                .acceptAccountPayments(true)
                .acceptCardPayments(true)
                .acceptMpesaPayments(true)
                .acceptGHMobileMoneyPayments(true)
                .onStagingEnv(false)
                .allowSaveCardFeature(false)
                .isPreAuth(false)
                .onStagingEnv(true)
                .initialize()
    }

    override fun onAddMoneySuccess() {
        mActivityManageMoneyBinding!!.imgSuccess.visible()
        mActivityManageMoneyBinding!!.btnUpdate.text = "Close"
        mActivityManageMoneyBinding!!.toolbar.setToolbarTitle("Success")
        mActivityManageMoneyBinding!!.txtContactInfo.text = "Payment Successfully added to your wallet"
        mActivityManageMoneyBinding!!.etAmount.disable()
    }

    private fun onPermissionCheck(payableAmount: Double) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED) {
            onAddMoney(payableAmount)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_PHONE_STATE),
                        READ_PHONE_STATE_PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {
            val message = data.getStringExtra("response")
            when (resultCode) {
                RavePayActivity.RESULT_SUCCESS -> {
                    val gsonBuilder = GsonBuilder()
                    val gson = gsonBuilder.create()
                    val newPayment = gson.fromJson(message, Payment::class.java)
                    val txref = newPayment.data.txRef
                    val amount = newPayment.data.amount
                    apiPaymentLink(txref, amount)
                }
                RavePayActivity.RESULT_ERROR -> Toast.makeText(this, "ERROR $message", Toast.LENGTH_SHORT).show()
                RavePayActivity.RESULT_CANCELLED -> Toast.makeText(this, "CANCELLED $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == READ_PHONE_STATE_PERMISSION_REQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val payableAmount = mActivityManageMoneyBinding!!.etAmount.text.toString().toDouble()
            onPermissionCheck(payableAmount)
        } else {
            viewModel.getSession().setSyncContact(false)
        }
    }

    private fun apiPaymentLink(txref: String, amount: String) {
        val req = JsonObject()
        req.addProperty("amount", amount)
        req.addProperty("txref", txref)

        viewModel.userAddMoney(req)
    }
}