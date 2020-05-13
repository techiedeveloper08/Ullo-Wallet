package com.ullo.api.response.contact;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ContactData {

    @SerializedName("contact_list")
    private List<Contact> contactList = null;
    @SerializedName("message")
    private String message;

    public List<Contact> getContactList() {
        return contactList;
    }

    public void setContactList(List<Contact> contactList) {
        this.contactList = contactList;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}