package com.devrachit.krishi.presentation.authScreens.loginScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.common.util.getActivity
import com.devrachit.krishi.domain.models.SharedViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(

    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth : FirebaseAuth,
    val storage : FirebaseStorage,
    val db: FirebaseFirestore,
): ViewModel(){
    val nameValid = mutableStateOf(true)
    val numberValid = mutableStateOf(true)
    val otpValid= mutableStateOf(true)
    val verificationToken = mutableStateOf<String>("")


    fun sendOTP(context: Context, phoneNumber: String)
    {
        viewModelScope.launch {
            try {
                val callback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                        Log.d("phoneBook","verification completed")
                    }

                    override fun onVerificationFailed(p0: FirebaseException) {
                        Log.d("phoneBook","verification failed" + p0)
                    }

                    override fun onCodeSent(verificationId: String,
                                            token: PhoneAuthProvider.ForceResendingToken) {
                        Log.d("phoneBook","code sent" + verificationId)
                        verificationToken.value = verificationId
                    }

                }
                val options = PhoneAuthOptions.newBuilder(auth)
                    .setPhoneNumber("+91"+phoneNumber)
                    .setTimeout(60L, TimeUnit.SECONDS)
                    .setActivity(context.getActivity())
                    .setCallbacks(callback)
                    .build()
                PhoneAuthProvider.verifyPhoneNumber(options)
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun verifyOTP(otp: String) {
        val credential = PhoneAuthProvider.getCredential(verificationToken.value, otp)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("phoneBook", "signInWithCredential:success")
                    sharedViewModel.setUserLoggedIn(true)
                } else {
                    Log.d("phoneBook", "signInWithCredential:failure", task.exception)
                }
            }
    }
}