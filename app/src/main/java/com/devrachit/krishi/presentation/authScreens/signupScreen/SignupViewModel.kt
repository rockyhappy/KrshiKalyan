package com.devrachit.krishi.presentation.authScreens.signupScreen

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.common.util.getActivity
import com.devrachit.krishi.domain.models.SharedViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth : FirebaseAuth,
    val storage : FirebaseStorage,

    application: Application,
) : AndroidViewModel(application){
    val nameValid = mutableStateOf(true)
    val numberValid = mutableStateOf(true)
    val tempAddressValid = mutableStateOf(true)
    val permAddressValid = mutableStateOf(true)
    val identificationNumberValid = mutableStateOf(true)
    val verificationToken = mutableStateOf<String>("")
    val otpNumberState = mutableStateOf(true)
    fun sendOTP(context: Context, phoneNumber: String) {
        Log.d("phoneBook",phoneNumber)
        viewModelScope.launch {
            try{
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
            catch(e: Exception){
                Log.d("phoneBook",e.toString())
            }
        }
    }



    fun verifyOTP( otp: String) {
        viewModelScope.launch{
            try{
                val credential = PhoneAuthProvider.getCredential(verificationToken.value, otp)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("phoneBook", "signInWithCredential:success")
                            val user = task.result?.user
                        } else {
                            Log.w("phoneBook", "signInWithCredential:failure", task.exception)
                        }
                    }
            }
            catch(e :Exception)
            {

            }
        }
    }

}