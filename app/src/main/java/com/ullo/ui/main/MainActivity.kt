package com.ullo.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonElement
import com.ullo.BR
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityMainBinding
import com.ullo.ui.balance_history.BalanceHistoryActivity
import com.ullo.ui.choose_contact.ChooseContactActivity
import com.ullo.ui.manage_money.ManageMoneyActivity
import com.ullo.ui.notification.NotificationActivity
import com.ullo.ui.setting.SettingActivity
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(), MainNavigator {

    companion object {
        private const val READ_CONTACT_PERMISSION_REQUEST_CODE = 76

        fun newIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: MainViewModel
        get() = ViewModelProviders.of(this, factory).get(MainViewModel::class.java)

    private var mActivityMainBinding: ActivityMainBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMainBinding = getViewDataBinding()
        viewModel.setNavigator(this)
        viewModel.getCms()

        viewModel.getSession().getAppUser()?.run {
            mActivityMainBinding!!.txtUserEmail.text = userData.email
            mActivityMainBinding!!.txtUserFullName.text = userData.fullName

            Glide.with(this@MainActivity)
                    .load(if (userData.image != null) userData.image else "")
                    .centerCrop()
                    .placeholder(R.drawable.user_icon)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mActivityMainBinding!!.imgUser)
        }

        viewModel.getAccountInfo().observe(this@MainActivity, Observer {
            onAccountInfoSuccess(it)
        })

        viewModel.run {
            if (!getSession().getSyncContact()) {
                getAllContact().observeForever {
                    if (it.isEmpty()) {
                        fetchContacts()
                    }
                }
            }
        }
    }

    private fun fetchContacts() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED) {
            viewModel.fetchContactAndUpload()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                        READ_CONTACT_PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == READ_CONTACT_PERMISSION_REQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchContacts()
        } else {
            viewModel.getSession().setSyncContact(false)
        }
    }

    override fun onMenuHandle() {
        SettingActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onNotificationHandle() {
        NotificationActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onSendMoneyHandle() {
        ChooseContactActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onReceiveMoneyHandle() {
        BalanceHistoryActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onMangeMoneyHandle() {
        ManageMoneyActivity.newIntent(this).apply {
            startActivity(this)
        }
    }

    override fun onAccountInfoSuccess(data: JsonElement) {
        if (data.asJsonObject.get("current_balance") != null) {
            mActivityMainBinding!!.txtCurrentBalance.text = "$" + data.asJsonObject.get("current_balance").asInt
            mActivityMainBinding!!.txtCreditTotal.text = "$" + data.asJsonObject.get("credit_balance").asInt
        }
    }
}
