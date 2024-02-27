package com.example.contactappmanagerapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.contactappmanagerapp.db.entity.Contact;
import com.example.contactappmanagerapp.db.entity.ContactDAO;

@Database(entities = {Contact.class}, version = 1)
public abstract class ContactDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
