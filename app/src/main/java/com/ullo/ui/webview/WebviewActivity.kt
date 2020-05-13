package com.ullo.ui.webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.lifecycle.ViewModelProviders
import com.ullo.BR
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityWebviewBinding
import com.ullo.utils.Constant
import com.ullo.utils.ViewModelProviderFactory
import javax.inject.Inject

class WebviewActivity : BaseActivity<ActivityWebviewBinding, WebviewViewModel>(), WebviewNavigator {

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, WebviewActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: WebviewViewModel
        get() = ViewModelProviders.of(this, factory).get(WebviewViewModel::class.java)

    private var mActivityWebviewBinding: ActivityWebviewBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_webview

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityWebviewBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        var title = ""
        var url = ""

        intent?.run {
            title = getStringExtra(Constant.PARAM_WEBVIEW_TITLE)
            url = getStringExtra(Constant.PARAM_WEBVIEW_URL)
        }

        mActivityWebviewBinding!!.toolbar.setToolbarTitle(title)
        mActivityWebviewBinding!!.toolbar.setBackButton(true)
        mActivityWebviewBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })

        with(mActivityWebviewBinding!!.webview) {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    }
}