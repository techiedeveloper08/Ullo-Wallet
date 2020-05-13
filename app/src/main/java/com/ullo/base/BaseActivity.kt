package com.ullo.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.ullo.App
import com.ullo.R
import com.ullo.utils.ConnectionLiveData
import com.ullo.utils.ShowLoadingOverlayDialog
import dagger.android.AndroidInjection
import io.reactivex.disposables.CompositeDisposable
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.app.Dialog
import android.view.LayoutInflater
import android.widget.TextView

abstract class BaseActivity<T : ViewDataBinding, V : BaseViewModel<*>> : AppCompatActivity(), BaseNavigator {

    private var viewDataBinding: T? = null
    private var mViewModel: V? = null

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract val bindingVariable: Int

    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V?

    abstract val isFullScreen: Boolean
    var isConnected: Boolean = false

    var compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var loadingOverlayDialog: Dialog? = null

    fun getViewDataBinding(): T {
        return viewDataBinding!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        if (isFullScreen) {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        super.onCreate(savedInstanceState)
        performDataBinding()
        registerRxBus()

        val connectionLiveData = ConnectionLiveData(this)
        connectionLiveData.observe(this, Observer { isConnected ->
            isConnected?.let {
                this.isConnected = it
            }
        })
    }

    fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun performDependencyInjection() {
        AndroidInjection.inject(this)
    }

    private fun performDataBinding() {
        viewDataBinding = DataBindingUtil.setContentView(this, layoutId)
        this.mViewModel = if (mViewModel == null) viewModel else mViewModel
        viewDataBinding!!.setVariable(bindingVariable, mViewModel)
        viewDataBinding!!.executePendingBindings()
    }

    override fun handleError(error: String) {
        AlertDialog.Builder(this)
                .setTitle("Opps!")
                .setIcon(R.drawable.error)
                .setMessage(error)
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }

    override fun onInternetConnectionError() {
        AlertDialog.Builder(this)
                .setTitle("Opps!")
                .setIcon(R.drawable.error)
                .setMessage(getString(R.string.please_check_your_internet_connection_or_try_again_later))
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }

    fun onShowAlert(title: String, msg: String) {
        AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setPositiveButton(android.R.string.yes) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
    }

    private fun registerRxBus() {
        compositeDisposable.add(App.instance.rxBus().toObservable().subscribe { any ->
            if (any is String) {
                if (any == HIDE_LOADING_OVERLAY_DIALOG) {
                    hideLoadingOverlayDialog()
                }
            }
            if (any is ShowLoadingOverlayDialog) {
                showLoadingOverlayDialog(any.loadingMessage)
            }
        })
    }

    private fun hideLoadingOverlayDialog() {
        if (loadingOverlayDialog != null) {
            if (loadingOverlayDialog!!.isShowing) {
                loadingOverlayDialog!!.dismiss()
            }
            loadingOverlayDialog = null
        }
    }

    @Synchronized
    private fun showLoadingOverlayDialog(msg: String) {
        if (loadingOverlayDialog != null && loadingOverlayDialog!!.isShowing) {  // making sure we have only one instance.
            return
        }
        try {
            // call when you press back while dialog is visible
            loadingOverlayDialog = Dialog(this)
            loadingOverlayDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val view = LayoutInflater.from(this).inflate(R.layout.custom_progress_dialog,null)
            view.findViewById<TextView>(R.id.message).text = msg
            loadingOverlayDialog!!.setContentView(view)
            loadingOverlayDialog!!.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            loadingOverlayDialog!!.setCancelable(false)
            loadingOverlayDialog!!.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (compositeDisposable != null) {
            compositeDisposable.dispose()
        }
        hideLoadingOverlayDialog()
    }

    companion object {
        const val HIDE_LOADING_OVERLAY_DIALOG = "hideLoadingOverlayDialog"
    }
}