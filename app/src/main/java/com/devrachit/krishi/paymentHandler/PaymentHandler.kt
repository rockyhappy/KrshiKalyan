package com.devrachit.krishi.paymentHandler

import org.json.JSONObject


interface PaymentHandler {
    fun startRazorpayPayment(options: JSONObject)
}
