package com.samsad.contactly.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.samsad.contactly.model.Contact;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao getUserDao();
}
