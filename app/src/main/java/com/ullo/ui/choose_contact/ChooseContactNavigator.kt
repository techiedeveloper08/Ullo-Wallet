package com.ullo.ui.choose_contact

import com.ullo.api.response.contact.Contact
import com.ullo.base.BaseNavigator


interface ChooseContactNavigator : BaseNavigator {
    fun onContactSuccessfully()
    fun setFilterPatientList(filteredDataList: ArrayList<Contact>)
    fun onScanQrCodeHandle()
}