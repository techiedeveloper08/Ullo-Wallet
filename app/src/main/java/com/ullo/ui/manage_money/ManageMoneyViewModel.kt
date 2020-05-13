package com.ullo.ui.manage_money

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
import retrofit2.Response


class ManageMoneyViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<ManageMoneyNavigator>(application, ulloService, session, dataManager) {

    fun userAddMoney(registerReq: JsonObject) {
        App.instance.showLoadingOverlayDialog(App.instance.getString(R.string.adding_money))
        getCompositeDisposable()?.add(getUlloService().userAddMoney(registerReq, object : ResponseListener<Response<BaseResponse<JsonElement>>, String> {
            override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                App.instance.hideLoadingOverlayDialog()
                if (response.isSuccessful) {
                    response.body()?.run {
                        getNavigator()?.onAddMoneySuccess()
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