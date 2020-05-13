package com.ullo.ui.setting

import android.app.Application
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.ullo.App
import com.ullo.R
import com.ullo.api.ResponseListener
import com.ullo.api.response.BaseResponse
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class SettingViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<SettingNavigator>(application, ulloService, session, dataManager) {

    fun onMyProfileButtonClick() {
        getNavigator()?.onMyProfileHandle()
    }

    fun onMyQRCodeButtonClick() {
        getNavigator()?.onMyQRCodeHandle()
    }

    fun onAddCardButtonClick() {
        getNavigator()?.onAddCardHandle()
    }

    fun onChangePasswordButtonClick() {
        getNavigator()?.onChangePasswordHandle()
    }

    fun onTermsConditionButtonClick() {
        getNavigator()?.onTermsConditionHandle()
    }

    fun onPrivacyPolicesButtonClick() {
        getNavigator()?.onPrivacyPolicesHandle()
    }

    fun onContactUsButtonClick() {
        getNavigator()?.onContactUsHandle()
    }

    fun onAboutUsButtonClick() {
        getNavigator()?.onAboutUsHandle()
    }

    fun onSignOutButtonClick() {
        getCompositeDisposable()?.add(getDataManager().deleteAllTables()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it) {
                        getSession().logout()
                        getNavigator()?.onSignOutHandle()
                    }
                })
    }

    fun userNotificationSetting(registerReq: JsonObject) {
        App.instance.showLoadingOverlayDialog(App.instance.getString(R.string.loading))
        getCompositeDisposable()?.add(getUlloService().userNotificationSetting(registerReq, object : ResponseListener<Response<BaseResponse<JsonElement>>, String> {
            override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                App.instance.hideLoadingOverlayDialog()
                if (response.isSuccessful) {
                    response.body()?.run {
                        getNavigator()?.onNotificationSettingSuccess()
                    }
                }
            }

            override fun onInternetConnectionError() {
                App.instance.hideLoadingOverlayDialog()
                getNavigator()?.onInternetConnectionError()
            }

            override fun onFailure(error: String) {
                App.instance.hideLoadingOverlayDialog()
                getNavigator()?.handleError(error)
            }
        }))
    }
}