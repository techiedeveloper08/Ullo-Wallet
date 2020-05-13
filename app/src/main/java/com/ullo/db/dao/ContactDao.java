package com.ullo.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ullo.api.response.contact.Contact;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT phoneNumber FROM contact WHERE phoneNumber = :phoneNumber LIMIT 1")
    String getItemId(String phoneNumber);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Contact contact);

    @Query("UPDATE contact SET userId =:userId, fullName =:fullName, image =:image, countryCode =:countryCode WHERE phoneNumber =:phoneNumber")
    int update(String userId,String fullName, String image, String countryCode, String phoneNumber);

    @Query("DELETE FROM contact WHERE pid = :contactId")
    int deletePatientById(Integer contactId);

    @Query("SELECT * FROM contact")
    LiveData<List<Contact>> loadAll();
}
