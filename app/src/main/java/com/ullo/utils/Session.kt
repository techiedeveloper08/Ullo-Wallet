package com.ullo.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.ullo.api.response.AppUser
import com.ullo.api.response.CmsData
import com.ullo.utils.Session.Key.APP_ACCOUNT_INFO
import com.ullo.utils.Session.Key.APP_CMS_DATA
import com.ullo.utils.Session.Key.APP_DEVICE_ID
import com.ullo.utils.Session.Key.APP_EMAIL_NOTIFICATION
import com.ullo.utils.Session.Key.APP_PUSH_NOTIFICATION
import com.ullo.utils.Session.Key.APP_SHOW_TUTORIAL
import com.ullo.utils.Session.Key.APP_SYNC_CONTACT
import com.ullo.utils.Session.Key.APP_USER
import com.ullo.utils.Session.Key.APP_USER_TOKEN

class Session(context: Context) {

    private val PREF_NAME = "private_lindera"
    private val PRIVATE_MODE = 0
    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor
    private val context: Context = context

    init {
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        editor = pref.edit()
    }

    fun setAppUser(appUser: AppUser) {
        editor.putString(APP_USER, SharedPreferenceHelper.getStringFromObject(appUser))
        editor.apply()
    }

    fun getAppUser(): AppUser {
        return SharedPreferenceHelper.getObjectFromString(pref.getString(APP_USER, ""), object :
                TypeToken<AppUser>() {
        })
    }

    fun setAppCmsData(cmsData: CmsData) {
        editor.putString(APP_CMS_DATA, SharedPreferenceHelper.getStringFromObject(cmsData))
        editor.apply()
    }

    fun getAppCmsData(): CmsData {
        return SharedPreferenceHelper.getObjectFromString(pref.getString(APP_CMS_DATA, ""), object :
                TypeToken<CmsData>() {
        })
    }

    fun setAccountInfo(cmsData: JsonElement) {
        editor.putString(APP_ACCOUNT_INFO, SharedPreferenceHelper.getStringFromObject(cmsData))
        editor.apply()
    }

    fun getAccountInfo(): JsonElement {
        return SharedPreferenceHelper.getObjectFromString(pref.getString(APP_ACCOUNT_INFO, ""), object :
                TypeToken<JsonElement>() {
        })
    }

    fun setAppUserToken(token: String) {
        editor.putString(APP_USER_TOKEN, token)
        editor.apply()
    }

    fun getAppUserToken(): String {
        return pref.getString(APP_USER_TOKEN, "")
    }

    fun setAppDeviceId(token: String) {
        editor.putString(APP_DEVICE_ID, token)
        editor.apply()
    }

    fun getAppDeviceId(): String {
        return pref.getString(APP_DEVICE_ID, "")
    }

    fun setShowTutorial(b: Boolean) {
        editor.putBoolean(APP_SHOW_TUTORIAL, b)
        editor.apply()
    }

    fun getShowTutorial(): Boolean {
        return pref.getBoolean(APP_SHOW_TUTORIAL, false)
    }

    fun setSyncContact(b: Boolean) {
        editor.putBoolean(APP_SYNC_CONTACT, b)
        editor.apply()
    }

    fun getSyncContact(): Boolean {
        return pref.getBoolean(APP_SYNC_CONTACT, false)
    }

    fun setPushNotification(b: Boolean) {
        editor.putBoolean(APP_PUSH_NOTIFICATION, b)
        editor.apply()
    }

    fun getPushNotification(): Boolean {
        return pref.getBoolean(APP_PUSH_NOTIFICATION, false)
    }

    fun setEmailNotification(b: Boolean) {
        editor.putBoolean(APP_EMAIL_NOTIFICATION, b)
        editor.apply()
    }

    fun getEmailNotification(): Boolean {
        return pref.getBoolean(APP_EMAIL_NOTIFICATION, false)
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }

    object Key {
        internal const val APP_USER = "app_user"
        internal const val APP_USER_TOKEN = "app_user_token"
        internal const val APP_DEVICE_ID = "app_device_id"
        internal const val APP_SHOW_TUTORIAL = "app_show_tutorial"
        internal const val APP_CMS_DATA = "app_cms_data"
        internal const val APP_ACCOUNT_INFO = "app_account_info"
        internal const val APP_SYNC_CONTACT = "app_sync_contact"
        internal const val APP_PUSH_NOTIFICATION = "app_push_notification"
        internal const val APP_EMAIL_NOTIFICATION = "app_email_notification"
    }
}