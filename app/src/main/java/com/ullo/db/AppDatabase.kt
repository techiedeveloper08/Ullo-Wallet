package com.ullo.db

import androidx.room.Database
import androidx.room.RoomDatabase

import com.ullo.api.response.contact.Contact
import com.ullo.db.dao.ContactDao

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    internal abstract fun contactDao(): ContactDao
}
