package com.ullo.ui.profile

import android.app.Application
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.ullo.App
import com.ullo.R
import com.ullo.api.ResponseListener
import com.ullo.api.response.AppUser
import com.ullo.api.response.BaseResponse
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session
import com.ullo.utils.SharedPreferenceHelper
import retrofit2.Response
import java.io.File


class ProfileViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<ProfileNavigator>(application, ulloService, session, dataManager) {

    fun onUpdateButtonClick() {
        getNavigator()?.onUpdateHandle()
    }

    fun userUpdateProfile(registerReq: JsonObject) {
        App.instance.showLoadingOverlayDialog(App.instance.getString(R.string.loading))
        getCompositeDisposable()?.add(getUlloService().userUpdateProfile(registerReq, object : ResponseListener<Response<BaseResponse<AppUser>>, String> {
            override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                App.instance.hideLoadingOverlayDialog()
                if (response.isSuccessful) {
                    response.body()?.let {
                        getSession().setAppUser(it.data)
                        getSession().setAppUserToken(it.data.token.accessToken)
                        getNavigator()?.onUpdateSuccessful(it.data)
                    }
                } else {
                    try {
                        response.errorBody()?.run {
                            val errorResponse = SharedPreferenceHelper.getObjectFromString(string(), object : TypeToken<BaseResponse<JsonElement>>() {})
                            getNavigator()?.handleError(errorResponse.error.asJsonArray[0].asString)
                        }
                    } catch (e: Exception) {
                        getNavigator()?.handleError(e.message!!)
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

    fun userUploadImage(registerReq: JsonObject, file: File) {
        App.instance.showLoadingOverlayDialog(App.instance.getString(R.string.loading))
        getCompositeDisposable()?.add(getUlloService().userUploadImage(registerReq, file, object : ResponseListener<Response<BaseResponse<AppUser>>, String> {
            override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                App.instance.hideLoadingOverlayDialog()
                if (response.isSuccessful) {
                    response.body()?.let {
                        getSession().setAppUser(it.data)
                        getSession().setAppUserToken(it.data.token.accessToken)
                        getNavigator()?.onUpdateSuccessful(it.data)
                    }
                } else {
                    try {
                        response.errorBody()?.run {
                            val errorResponse = SharedPreferenceHelper.getObjectFromString(string(), object : TypeToken<BaseResponse<JsonElement>>() {})
                            getNavigator()?.handleError(errorResponse.error.asJsonArray[0].asString)
                        }
                    } catch (e: Exception) {
                        getNavigator()?.handleError(e.message!!)
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