package com.ullo.api.response

import com.google.gson.annotations.SerializedName

data class Payment(@SerializedName("data")
                   val data: Data,
                   @SerializedName("message")
                   val message: String,
                   @SerializedName("status")
                   val status: String)

data class Data(@SerializedName("charge_type")
                val charge_type: String,
                @SerializedName("redirectUrl")
                val redirectUrl: String,
                @SerializedName("AccountId")
                val accountId: String,
                @SerializedName("merchantfee")
                val merchantfee: String,
                @SerializedName("acctvalrespcode")
                val acctvalrespcode: String,
                @SerializedName("createdAt")
                val createdAt: String,
                @SerializedName("charged_amount")
                val charged_amount: String,
                @SerializedName("paymentId")
                val paymentId: String,
                @SerializedName("id")
                val id: String,
                @SerializedName("updatedAt")
                val updatedAt: String,
                @SerializedName("appfee")
                val appfee: String,
                @SerializedName("merchantbearsfee")
                val merchantbearsfee: String,
                @SerializedName("IP")
                val IP: String,
                @SerializedName("raveRef")
                val raveRef: String,
                @SerializedName("chargeResponseMessage")
                val chargeResponseMessage: String,
                @SerializedName("authurl")
                val authurl: String,
                @SerializedName("status")
                val status: String,
                @SerializedName("fraud_status")
                val fraud_status: String,
                @SerializedName("chargeResponseCode")
                val chargeResponseCode: String,
                @SerializedName("vbvrespcode")
                val vbvrespcode: String,
                @SerializedName("cycle")
                val cycle: String,
                @SerializedName("paymentType")
                val paymentType: String,
                @SerializedName("flwRef")
                val flwRef: String,
                @SerializedName("customerId")
                val customerId: String,
                @SerializedName("authModelUsed")
                val authModelUsed: String,
                @SerializedName("currency")
                val currency: String,
                @SerializedName("device_fingerprint")
                val device_fingerprint: String,
                @SerializedName("amount")
                val amount: String,
                @SerializedName("modalauditid")
                val modalauditid: String,
                @SerializedName("vbvrespmessage")
                val vbvrespmessage: String,
                @SerializedName("orderRef")
                val orderRef: String,
                @SerializedName("txRef")
                val txRef: String,
                @SerializedName("narration")
                val narration: String,
                @SerializedName("is_live")
                val is_live: String)