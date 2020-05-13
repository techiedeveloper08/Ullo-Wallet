
package com.ullo.api.response.balance_history;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BalanceHistoryData {

    @SerializedName("history_list")
    @Expose
    private List<History> history = null;
    @SerializedName("is_last")
    @Expose
    private Integer isLast;
    @SerializedName("message")
    @Expose
    private String message;

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
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
