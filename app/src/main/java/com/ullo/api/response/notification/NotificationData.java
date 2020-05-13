
package com.ullo.api.response.notification;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationData {

    @SerializedName("notification_list")
    @Expose
    private List<Notification> notification = null;
    @SerializedName("is_last")
    @Expose
    private Integer isLast;
    @SerializedName("message")
    @Expose
    private String message;

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public Integer getIsLast() {
        return isLast;
    }

    public void setIsLast(Integer isLast) {
        this.isLast = isLast;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
