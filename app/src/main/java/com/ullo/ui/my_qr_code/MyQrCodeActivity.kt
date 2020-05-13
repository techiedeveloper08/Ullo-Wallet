package com.ullo.ui.my_qr_code

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ullo.BR
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityMyQrCodeBinding
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class MyQrCodeActivity : BaseActivity<ActivityMyQrCodeBinding, MyQrCodeViewModel>(), MyQrCodeNavigator {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MyQrCodeActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: MyQrCodeViewModel
        get() = ViewModelProviders.of(this, factory).get(MyQrCodeViewModel::class.java)

    private var mActivityMyQrCodeBinding: ActivityMyQrCodeBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_my_qr_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityMyQrCodeBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        mActivityMyQrCodeBinding!!.toolbar.setBackButton(true)
        mActivityMyQrCodeBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })

        viewModel.getSession().getAppUser()?.run {
            Glide.with(this@MyQrCodeActivity).load(userData.qrImage).into(mActivityMyQrCodeBinding!!.imgQrCode)
        }
    }
}