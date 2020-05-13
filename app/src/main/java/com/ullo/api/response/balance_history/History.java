
package com.ullo.api.response.balance_history;

import android.text.format.DateFormat;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Locale;

public class History {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        if (type.equals("C")) {
            return "+ $" + amount;
        } else {
            return "- $" + amount;
        }
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTimestamp() {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Integer.parseInt(timestamp) * 1000L);
        String date = DateFormat.format("dd. MMM. yyyy", cal).toString();
        return date;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
