package com.samsad.contactly.model;

import java.util.ArrayList;
import java.util.Arrays;

public class SaveData {

    private Contact[] contacts;

    public Contact[] getContacts() {
        return contacts;
    }

    public void setContacts(Contact[] contacts) {
        this.contacts = contacts;
    }


    public ArrayList<Contact> getContactList() {
        ArrayList<Contact> arrayList = null;
        try {
            if(contacts != null) {
                arrayList = new ArrayList<Contact>(Arrays.asList(contacts));
            }
        } catch (Exception e){
            arrayList = null;
        }

        return arrayList;
    }
}
