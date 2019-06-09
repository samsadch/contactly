package com.samsad.contactly.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.samsad.contactly.model.Contact;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    public void addContact(Contact user);
    //method performs database insertion

    @Query("select * from `contact`")
    public List<Contact> getAllContacts();

    @Delete
    public void deleteContact(Contact user);

    @Update
    public void updateContact(Contact user);
}
