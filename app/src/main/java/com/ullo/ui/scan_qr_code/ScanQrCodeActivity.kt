package com.ullo.ui.scan_qr_code

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.google.zxing.Result
import com.ullo.BR
import com.ullo.R
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityScanQrCodeBinding
import com.ullo.extensions.gone
import com.ullo.extensions.visible
import com.ullo.utils.Validation
import com.ullo.utils.ViewModelProviderFactory
import me.dm7.barcodescanner.zxing.ZXingScannerView
import javax.inject.Inject

class ScanQrCodeActivity : BaseActivity<ActivityScanQrCodeBinding, ScanQrCodeViewModel>(), ScanQrCodeNavigator, ZXingScannerView.ResultHandler {

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 77
        fun newIntent(context: Context): Intent {
            return Intent(context, ScanQrCodeActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: ScanQrCodeViewModel
        get() = ViewModelProviders.of(this, factory).get(ScanQrCodeViewModel::class.java)

    private var mActivityScanQrCodeBinding: ActivityScanQrCodeBinding? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_scan_qr_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityScanQrCodeBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) ==
                PackageManager.PERMISSION_GRANTED) {

        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA),
                        CAMERA_PERMISSION_REQUEST_CODE)
            }
        }

        mActivityScanQrCodeBinding!!.scannerview.keepScreenOn = true
    }

    override fun onResume() {
        super.onResume()
        mActivityScanQrCodeBinding!!.scannerview.setResultHandler(this@ScanQrCodeActivity) // Register ourselves as a handler for scan results.
        mActivityScanQrCodeBinding!!.scannerview.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mActivityScanQrCodeBinding!!.scannerview.stopCamera()           // Stop camera on pause
    }

    override fun handleResult(p0: Result?) {
        Log.d("mytag", "Result::$p0")
    }

    override fun onCloseHandle() {
        finish()
    }

    override fun onSendHandle() {
        if (isValid()) {
            Log.d("mytag", "Valid Number")
        }
    }

    private fun isValid(): Boolean {
        var bCountryCode = true
        val mobileNo = mActivityScanQrCodeBinding!!.etMobileNo.text.toString()
        val countryCode = mActivityScanQrCodeBinding!!.etCountryCode.selectedCountryCode

        if (countryCode.isEmpty()) {
            mActivityScanQrCodeBinding!!.tIMobileNo.visible()
            mActivityScanQrCodeBinding!!.tIMobileNo.text = "Not valid country code"
            bCountryCode = false
        } else {
            mActivityScanQrCodeBinding!!.tIMobileNo.gone()
        }

        if (mobileNo.isEmpty() || !Validation.isValidMobile(mobileNo)) {
            mActivityScanQrCodeBinding!!.tIMobileNo.visible()
            mActivityScanQrCodeBinding!!.tIMobileNo.text = "Not valid mobile number"
            bCountryCode = false
        } else {
            mActivityScanQrCodeBinding!!.tIMobileNo.gone()
        }

        return bCountryCode
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mActivityScanQrCodeBinding!!.scannerview.startCamera()
        }
    }
}