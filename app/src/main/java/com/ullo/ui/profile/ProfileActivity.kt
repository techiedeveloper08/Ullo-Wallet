package com.ullo.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonObject
import com.ullo.BR
import com.ullo.BuildConfig
import com.ullo.R
import com.ullo.api.response.AppUser
import com.ullo.base.BaseActivity
import com.ullo.databinding.ActivityProfileBinding
import com.ullo.extensions.gone
import com.ullo.extensions.visible
import com.ullo.utils.ImageFilePath
import com.ullo.utils.Validation
import com.ullo.utils.ViewModelProviderFactory
import java.io.File
import javax.inject.Inject


class ProfileActivity : BaseActivity<ActivityProfileBinding, ProfileViewModel>(), ProfileNavigator {

    companion object {
        private const val READ_STORAGE_PERMISSION_REQUEST_CODE = 76
        const val GET_FROM_GALLERY = 101
        fun newIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }
    }

    @set:Inject
    lateinit var factory: ViewModelProviderFactory

    override val viewModel: ProfileViewModel
        get() = ViewModelProviders.of(this, factory).get(ProfileViewModel::class.java)

    private var mActivityProfileBinding: ActivityProfileBinding? = null
    private var file: File? = null

    override val bindingVariable: Int
        get() = BR.viewModel

    override val isFullScreen: Boolean
        get() = false

    override val layoutId: Int
        get() = R.layout.activity_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityProfileBinding = getViewDataBinding()
        viewModel.setNavigator(this)

        mActivityProfileBinding!!.toolbar.setBackButton(true)
        mActivityProfileBinding!!.toolbar.setBackButtonListener(listener = View.OnClickListener {
            finish()
        })

        mActivityProfileBinding!!.imgUser.setOnClickListener {
            selectImageFromGalary()
        }

        viewModel.getSession().getAppUser()?.run {
            mActivityProfileBinding!!.etFullName.setText(userData.fullName)
            mActivityProfileBinding!!.etEmail.setText(userData.email)
            mActivityProfileBinding!!.etCountryCode.setCountryForPhoneCode(userData.countryCode.toInt())
            mActivityProfileBinding!!.etMobileNo.setText(userData.phoneNumber)

            Glide.with(this@ProfileActivity)
                    .load(userData.image ?: "")
                    .centerCrop()
                    .placeholder(R.drawable.user_icon)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mActivityProfileBinding!!.imgUser)
        }
    }

    private fun selectImageFromGalary() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY)
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        READ_STORAGE_PERMISSION_REQUEST_CODE)
            }
        }
    }

    override fun onUpdateHandle() {
        if (isValid()) {
            val loginReq = JsonObject()
            loginReq.addProperty("full_name", mActivityProfileBinding!!.etFullName.text.toString())
            loginReq.addProperty("email", mActivityProfileBinding!!.etEmail.text.toString())
            loginReq.addProperty("country_code", "+" + mActivityProfileBinding!!.etCountryCode.selectedCountryCode)
            loginReq.addProperty("phone_number", mActivityProfileBinding!!.etMobileNo.text.toString())
            loginReq.addProperty("user_type", BuildConfig.USER_TYPE)
            loginReq.addProperty("device_id", viewModel.getSession().getAppDeviceId())
            loginReq.addProperty("device_type", BuildConfig.DEVICE_TYPE)
            if (file == null) {
                viewModel.userUpdateProfile(loginReq)
            } else {
                viewModel.userUploadImage(loginReq, file!!)
            }
        }
    }

    private fun isValid(): Boolean {
        var bFullName = true
        var bEmail = true
        var bCountry = true
        var bMobile = true
        val firstName = mActivityProfileBinding!!.etFullName.text.toString()
        val email = mActivityProfileBinding!!.etEmail.text.toString()
        val country = mActivityProfileBinding!!.etCountryCode.selectedCountryCode
        val mobileNo = mActivityProfileBinding!!.etMobileNo.text.toString()

        if (!Validation.isValidName(firstName)) {
            mActivityProfileBinding!!.tIFullName.isErrorEnabled = true
            mActivityProfileBinding!!.tIFullName.error = "The entered Name is not correct."
            bFullName = false
        } else {
            mActivityProfileBinding!!.tIFullName.isErrorEnabled = false
        }

        if (!Validation.isValidEmail(email)) {
            mActivityProfileBinding!!.tIEmail.isErrorEnabled = true
            mActivityProfileBinding!!.tIEmail.error = "The entered Email is not correct."
            bEmail = false
        } else {
            mActivityProfileBinding!!.tIEmail.isErrorEnabled = false
        }

        if (country.isEmpty()) {
            mActivityProfileBinding!!.tIMobileNo.visible()
            mActivityProfileBinding!!.tIMobileNo.error = "Not Valid Country Code"
            bCountry = false
        } else {
            mActivityProfileBinding!!.tIMobileNo.gone()
        }

        if (mobileNo.isEmpty() || !Validation.isValidMobile(mobileNo)) {
            mActivityProfileBinding!!.tIMobileNo.visible()
            mActivityProfileBinding!!.tIMobileNo.error = "Not Valid Number"
            bMobile = false
        } else {
            mActivityProfileBinding!!.tIMobileNo.gone()
        }

        return bFullName && bEmail && bCountry && bMobile
    }

    override fun onUpdateSuccessful(data: AppUser) {
        handleError("Profile Successfully Updated")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            val realPath = ImageFilePath.getPath(this, data!!.data)
            if (realPath != null) {
                file = File(realPath)
                Glide.with(this@ProfileActivity)
                        .load(file)
                        .centerCrop()
                        .placeholder(R.drawable.user_icon)
                        .apply(RequestOptions.circleCropTransform())
                        .into(mActivityProfileBinding!!.imgUser)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == READ_STORAGE_PERMISSION_REQUEST_CODE
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startActivityForResult(Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY)
        }
    }
}