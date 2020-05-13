package com.ullo.ui.main

import android.app.Application
import com.github.tamir7.contacts.Contact
import com.github.tamir7.contacts.Contacts
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.ullo.App
import com.ullo.R
import com.ullo.api.ResponseListener
import com.ullo.api.response.BaseResponse
import com.ullo.api.response.CmsData
import com.ullo.api.response.contact.ContactData
import com.ullo.api.service.UlloService
import com.ullo.base.BaseViewModel
import com.ullo.db.DataManager
import com.ullo.utils.Session
import com.ullo.utils.SharedPreferenceHelper
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response


class MainViewModel(application: Application, ulloService: UlloService, session: Session, dataManager: DataManager) : BaseViewModel<MainNavigator>(application, ulloService, session, dataManager) {

    fun onMenuButtonClick() {
        getNavigator()?.onMenuHandle()
    }

    fun onNotificationButtonClick() {
        getNavigator()?.onNotificationHandle()
    }

    fun onSendMoneyButtonClick() {
        getNavigator()?.onSendMoneyHandle()
    }

    fun onReceiveMoneyButtonClick() {
        getNavigator()?.onReceiveMoneyHandle()
    }

    fun onManageMoneyButtonClick() {
        getNavigator()?.onMangeMoneyHandle()
    }

    fun getCms() {
        getCompositeDisposable()?.add(getUlloService().userCMS(object : ResponseListener<Response<BaseResponse<CmsData>>, String> {
            override fun onSuccess(response: Response<BaseResponse<CmsData>>) {
                if (response.isSuccessful) {
                    response.body()?.run {
                        getSession()?.setAppCmsData(data)
                    }
                }
            }

            override fun onInternetConnectionError() {
                //BackGround Call
            }

            override fun onFailure(error: String) {
                //BackGround Call
            }
        }))
    }

    fun fetchContactAndUpload() {
        val q = Contacts.getQuery()
        q.hasPhoneNumber()
        getCompositeDisposable()?.run {
            add(Observable.just(q.find()).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe {
                        uploadContact(it)
                    })
        }
    }

    private fun uploadContact(contactList: List<Contact>) {
        var phoneNumberStr = ""
        contactList.forEach { contact ->
            contact.phoneNumbers.forEach { phoneNumber ->
                if (phoneNumber.normalizedNumber != null) {
                    phoneNumberStr += "," + removeCountryCodeFromPhoneNumber(phoneNumber.normalizedNumber)
                }
            }
        }
        val contactReq = JsonObject()
        contactReq.addProperty("phone_numbers", phoneNumberStr)
        userContactlist(contactReq)
    }

    private fun userContactlist(registerReq: JsonObject) {
        App.instance.showLoadingOverlayDialog(App.instance.getString(R.string.loading))
        getCompositeDisposable()?.add(getUlloService().userContactlist(registerReq, object : ResponseListener<Response<BaseResponse<ContactData>>, String> {
            override fun onSuccess(response: Response<BaseResponse<ContactData>>) {
                App.instance.hideLoadingOverlayDialog()
                if (response.isSuccessful) {
                    response.body()?.run {
                        definePatientLists(data.contactList)
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

    fun definePatientLists(list: List<com.ullo.api.response.contact.Contact>) {
        getCompositeDisposable()?.run {
            add(getDataManager().savePatientList(list)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        if (it) {
                            getSession().setSyncContact(true)
                        }
                    })
        }
    }

    private fun removeCountryCodeFromPhoneNumber(number: String): String {
        return if (number.startsWith("+")) {
            return when {
                number.length == 13 -> number.substring(3)
                number.length == 14 -> number.substring(4)
                else -> number
            }
        } else {
            number
        }
    }
}