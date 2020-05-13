package com.ullo.ui.send_money

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


class SendMoneyViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<SendMoneyNavigator>(application, ulloService, session, dataManager) {

    fun userSendMoney(registerReq: JsonObject) {
        App.instance.showLoadingOverlayDialog(App.instance.getString(R.string.sending_money))
        getCompositeDisposable()?.add(getUlloService().userSendMoney(registerReq, object : ResponseListener<Response<BaseResponse<JsonElement>>, String> {
            override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                App.instance.hideLoadingOverlayDialog()
                if (response.isSuccessful) {
                    response.body()?.run {
                        getNavigator()?.onSendMoneySuccess()
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