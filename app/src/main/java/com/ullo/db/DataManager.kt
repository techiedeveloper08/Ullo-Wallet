package com.ullo.db

import androidx.lifecycle.LiveData
import com.ullo.api.response.contact.Contact
import io.reactivex.Observable

class DataManager(private var appDbHelper: AppDbHelper) {

    val allContacts: LiveData<List<Contact>>
        get() = appDbHelper.allContacts

    fun savePatientList(contactList: List<Contact>): Observable<Boolean> {
        return appDbHelper.savePatientList(contactList)
    }

    fun savePatient(item: Contact) {
        return appDbHelper.savePatient(item)
    }

    fun deletePatientById(patientId: Int) {
        return appDbHelper.deletePatientById(patientId)
    }

    fun deleteAllTables(): Observable<Boolean> {
        return appDbHelper.deleteAllTables()
    }
}
