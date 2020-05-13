package com.ullo.ui.notification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ullo.BR
import com.ullo.R
import com.ullo.adapter.NotificationAdapter
import com.ullo.api.response.notification.NotificationData
import com.ullo.api.response.notification.Notification
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityNotificationBinding
import com.ullo.ui.setting.SettingActivity
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class NotificationActivity : BaseActivity<ActivityNotificationBinding, NotificationViewModel>(), NotificationNavigator {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, NotificationActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: NotificationViewModel
        get() = ViewModelProviders.of(this, factory).get(NotificationViewModel::class.java)

    @set:Inject
    var notificationAdapter: NotificationAdapter? = null

    private var mActivityNotificationBinding: ActivityNotificationBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityNotificationBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        mActivityNotificationBinding!!.toolbar.setBackButton(true)
        mActivityNotificationBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })
        mActivityNotificationBinding!!.toolbar.setRightButtonListener(listener = View.OnClickListener {
            SettingActivity.newIntent(this).apply {
                startActivity(this)
            }
        })

        viewModel.run { userNotificationlist() }
    }

    private fun setNotificationData(patient: List<Notification>) {
        with(mActivityNotificationBinding!!.recyclerView) {
            layoutManager = LinearLayoutManager(this@NotificationActivity)
            adapter = notificationAdapter
        }

        notificationAdapter?.run { setNotificationListData(patient as ArrayList<Notification>) }
    }

    override fun onNotificationSuccessfully(data: NotificationData) {
        setNotificationData(data.notification)
    }
}