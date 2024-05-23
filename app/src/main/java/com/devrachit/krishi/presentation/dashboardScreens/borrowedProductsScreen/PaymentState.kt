package com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen

sealed class PaymentState {
    object Idle : PaymentState()
    object Loading : PaymentState()
    data class Success(val paymentId: String?) : PaymentState()
    data class Failure(val errorCode: Int, val errorMessage: String?) : PaymentState()
}