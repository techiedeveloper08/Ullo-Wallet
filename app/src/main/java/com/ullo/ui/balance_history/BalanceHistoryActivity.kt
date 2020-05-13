package com.ullo.ui.balance_history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.R
import com.ullo.adapter.BalanceHistoryAdapter
import com.ullo.api.response.balance_history.BalanceHistoryData
import com.ullo.api.response.balance_history.History
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityBalanceHistoryBinding
import com.ullo.ui.setting.SettingActivity
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class BalanceHistoryActivity : BaseActivity<ActivityBalanceHistoryBinding, BalanceHistoryViewModel>(), BalanceHistoryNavigator {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, BalanceHistoryActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: BalanceHistoryViewModel
        get() = ViewModelProviders.of(this, factory).get(BalanceHistoryViewModel::class.java)

    @set:Inject
    var balanceHistoryAdapter: BalanceHistoryAdapter? = null

    private var mActivityBalanceHistoryBinding: ActivityBalanceHistoryBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_balance_history

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBalanceHistoryBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        mActivityBalanceHistoryBinding!!.toolbar.setBackButton(true)
        mActivityBalanceHistoryBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })
        mActivityBalanceHistoryBinding!!.toolbar.setRightButtonListener(listener = View.OnClickListener {
            SettingActivity.newIntent(this).apply {
                startActivity(this)
            }
        })

        viewModel.run {
            val jsonReq = JsonObject();
            jsonReq.addProperty("type", "W")
            userBalanceHistorylist(jsonReq)
        }
    }

    private fun setBalanceHistoryData(patientList: List<History>) {
        with(mActivityBalanceHistoryBinding!!.recyclerView) {
            layoutManager = LinearLayoutManager(this@BalanceHistoryActivity)
            adapter = balanceHistoryAdapter
        }

        balanceHistoryAdapter?.run { setBalanceHistoryListData(patientList as ArrayList<History>) }
    }

    override fun onBalanceHistorySuccessfull(data: BalanceHistoryData) {
        setBalanceHistoryData(data.history)
    }
}