package com.ullo.api.service

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.ullo.api.ApiResponseCallbackWrapper
import com.ullo.api.ResponseListener
import com.ullo.api.response.AppUser
import com.ullo.api.response.BaseResponse
import com.ullo.api.response.CmsData
import com.ullo.api.response.balance_history.BalanceHistoryData
import com.ullo.api.response.contact.ContactData
import com.ullo.api.response.notification.NotificationData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File


class UlloService(private val ulloApi: UlloApi) {

    fun userRegister(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<AppUser>>, String>): Disposable {
        return ulloApi.userRegister(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<AppUser>>>() {

                    override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userVerifyOtp(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<JsonElement>>, String>): Disposable {
        return ulloApi.userVerifyOtp(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<JsonElement>>>() {

                    override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userLogin(loginReq: JsonObject, listener: ResponseListener<Response<BaseResponse<AppUser>>, String>): Disposable {
        return ulloApi.userLogin(loginReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<AppUser>>>() {

                    override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userForgotPassword(loginReq: JsonObject, listener: ResponseListener<Response<BaseResponse<AppUser>>, String>): Disposable {
        return ulloApi.userForgotPassword(loginReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<AppUser>>>() {

                    override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userChangePassword(changePasswordReq: JsonObject, listener: ResponseListener<Response<BaseResponse<AppUser>>, String>): Disposable {
        return ulloApi.userChangePassword(changePasswordReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<AppUser>>>() {

                    override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userUpdateProfile(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<AppUser>>, String>): Disposable {
        return ulloApi.userUpdateProfile(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<AppUser>>>() {

                    override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userCMS(listener: ResponseListener<Response<BaseResponse<CmsData>>, String>): Disposable {
        return ulloApi.userCMS()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<CmsData>>>() {

                    override fun onSuccess(response: Response<BaseResponse<CmsData>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userContactUs(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<JsonElement>>, String>): Disposable {
        return ulloApi.userContactUs(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<JsonElement>>>() {

                    override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userContactlist(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<ContactData>>, String>): Disposable {
        return ulloApi.userContactlist(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<ContactData>>>() {

                    override fun onSuccess(response: Response<BaseResponse<ContactData>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userNotificationlist(listener: ResponseListener<Response<BaseResponse<NotificationData>>, String>): Disposable {
        return ulloApi.userNotificationlist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<NotificationData>>>() {

                    override fun onSuccess(response: Response<BaseResponse<NotificationData>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userBalanceHistorylist(registerReq: JsonObject,listener: ResponseListener<Response<BaseResponse<BalanceHistoryData>>, String>): Disposable {
        return ulloApi.userBalanceHistorylist(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<BalanceHistoryData>>>() {

                    override fun onSuccess(response: Response<BaseResponse<BalanceHistoryData>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userAddMoney(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<JsonElement>>, String>): Disposable {
        return ulloApi.userAddMoney(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<JsonElement>>>() {

                    override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userSendMoney(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<JsonElement>>, String>): Disposable {
        return ulloApi.userSendMoney(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<JsonElement>>>() {

                    override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userAccountInfo(listener: ResponseListener<Response<BaseResponse<JsonElement>>, String>): Disposable {
        return ulloApi.userAccountInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<JsonElement>>>() {

                    override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userNotificationSetting(registerReq: JsonObject, listener: ResponseListener<Response<BaseResponse<JsonElement>>, String>): Disposable {
        return ulloApi.userNotificationSetting(registerReq)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<JsonElement>>>() {

                    override fun onSuccess(response: Response<BaseResponse<JsonElement>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    fun userUploadImage(registerReq: JsonObject, file: File, listener: ResponseListener<Response<BaseResponse<AppUser>>, String>): Disposable {
        val params = HashMap<String, RequestBody>()
        registerReq.entrySet().forEach {
            params[it.key] = createRequestBody(it.value.asString)
        }
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

        return ulloApi.userUploadImage(body, params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : ApiResponseCallbackWrapper<Response<BaseResponse<AppUser>>>() {

                    override fun onSuccess(response: Response<BaseResponse<AppUser>>) {
                        listener.onSuccess(response)
                    }

                    override fun onInternetConnectionError() {
                        listener.onInternetConnectionError()
                    }

                    override fun onFailure(error: String) {
                        listener.onFailure(error)
                    }
                })
    }

    private fun createRequestBody(s: String): RequestBody {
        return RequestBody.create(MediaType.parse("multipart/form-data"), s)
    }
}