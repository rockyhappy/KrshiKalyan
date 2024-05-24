package com.devrachit.krishi.presentation.authScreens

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.devrachit.krishi.navigation.auth.authNavHost
import com.devrachit.krishi.paymentHandler.PaymentHandler
import com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen.BorrowedProductsViewModel
import com.devrachit.krishi.ui.theme.KrishiTheme
import com.devrachit.krishi.ui.theme.primaryVariantColor
import com.devrachit.krishi.ui.theme.primaryVariantColor1
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
@ExperimentalMaterial3Api
class Auth : ComponentActivity(), PaymentResultListener {
    private val viewModel: BorrowedProductsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = primaryVariantColor1.toArgb()
            window.navigationBarColor = primaryVariantColor.toArgb()
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = false
            KrishiTheme {

                    val navController = rememberNavController()
                    authNavHost(navController)

            }
        }
    }
    private fun startRazorpayPayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_2sIcIwphd64DGF")

        val options = JSONObject()
        options.put("name", "Krishi Kalyaan")
        options.put("description", "Payment for items in the cart")
        options.put("currency", "INR")
        options.put("amount", "10000") // Amount in paise

        try {
            checkout.open(this, options)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "Error in payment initiation: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPaymentSuccess(razorpayPaymentId: String?) {
        Log.d("Payment", "Payment Successful $razorpayPaymentId")
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show()
        viewModel.onPaymentSuccess(razorpayPaymentId)
    }

    override fun onPaymentError(code: Int, response: String?) {
        Log.d("Payment", "Payment Failed $response")
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show()
        viewModel.onPaymentFailure(code, response)
    }


}

