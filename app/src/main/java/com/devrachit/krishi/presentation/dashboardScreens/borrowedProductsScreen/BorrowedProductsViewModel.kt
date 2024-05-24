package com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.domain.models.itemModel2
import com.devrachit.krishi.domain.models.userModel
import com.devrachit.krishi.paymentHandler.PaymentHandler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

@HiltViewModel
class BorrowedProductsViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth: FirebaseAuth,
    val storage: FirebaseStorage,
    val db: FirebaseFirestore,
): ViewModel() {

    val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val _dataFetch = MutableStateFlow(false)
    val dataFetch = _dataFetch.asStateFlow()

    private val _paymentState = MutableStateFlow<PaymentState>(PaymentState.Idle)
    val paymentState = _paymentState.asStateFlow()

    // Other states and methods...

    fun onPaymentSuccess(paymentId: String?) {
        _paymentState.value = PaymentState.Success(paymentId)
    }

    fun onPaymentFailure(errorCode: Int, errorMessage: String?) {
        _paymentState.value = PaymentState.Failure(errorCode, errorMessage)
    }
    fun fetchOwnerDetails(itemModel: itemModel2, onResult: (userModel) -> Unit) {
        viewModelScope.launch {
            try {
                db.collection("users").document(itemModel.ownerUid).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val userData = userModel(
                                name = document.getString("name")!!,
                                number = document.getString("phoneNumber")!!,
                                tempAddress = document.getString("tempAddress")!!,
                                permAddress = document.getString("permAddress")!!,
                                identificationNumber = document.getString("identificationNumber")!!,
                                identificationType = document.getString("identificationType")!!,
                                isBorrower = document.getBoolean("isBorrower")!!
                            )
                            onResult(userData)
                        }
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun payForProduct(itemModel: itemModel2, context: Context) {
        viewModelScope.launch {
            try {
                val checkout = Checkout()
                checkout.setKeyID("rzp_test_2sIcIwphd64DGF")
                val options = JSONObject()
                options.put("name", "Krishi Kalyaan")
                options.put("description", "Payment for items in the cart")
                options.put("currency", "INR")
                options.put("amount", "${itemModel.price.toInt()*itemModel.quantity.toInt()*itemModel.days.toInt()*100}") // Amount in paise

                try {
                    checkout.open(context as Activity, options)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(context, "Error in payment initiation: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    fun updatePaymentStatus(paymentId: String, itemModel:itemModel2) {
        viewModelScope.launch {
            try {
                db.collection("items").document(itemModel.uid).update("paid",true).addOnSuccessListener {
                    println("Payment Successful")
                     sharedViewModel.selfUploads2.value.forEach {
                         if (it.uid == itemModel.uid) {
                             it.paid = true
                         }
                     }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun reviewProduct(itemModel: itemModel2)
    {
        viewModelScope.launch {
            try {
                    println("Review Successful")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}