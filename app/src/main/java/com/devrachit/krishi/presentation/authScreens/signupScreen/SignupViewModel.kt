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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class SignupViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth : FirebaseAuth,
    val storage : FirebaseStorage,
    val db: FirebaseFirestore,

    application: Application,
) : AndroidViewModel(application){
    val nameValid = mutableStateOf(true)
    val numberValid = mutableStateOf(true)
    val tempAddressValid = mutableStateOf(true)
    val permAddressValid = mutableStateOf(true)
    val identificationNumberValid = mutableStateOf(true)
    val verificationToken = mutableStateOf<String>("")
    val otpNumberState = mutableStateOf(true)

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()
    fun sendOTP(context: Context, phoneNumber: String) {
        Log.d("phoneBook",phoneNumber)
        viewModelScope.launch {
            try{
                _loading.value = true
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
                _loading.value = false
            }
            catch(e: Exception){
                Log.d("phoneBook",e.toString())
            }
        }
    }



    fun verifyOTP( otp: String) {
        viewModelScope.launch{
            try{
                _loading.value = true
                val credential = PhoneAuthProvider.getCredential(verificationToken.value, otp)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("phoneBook", "signInWithCredential:success")
                            val user = task.result?.user
                            db.collection("users").document(user?.uid.toString()).set(
                                hashMapOf(
                                    "name" to sharedViewModel.user.value?.name,
                                    "phoneNumber" to sharedViewModel.user.value?.number,
                                    "tempAddress" to sharedViewModel.user.value?.tempAddress,
                                    "permAddress" to sharedViewModel.user.value?.permAddress,
                                    "identificationType" to sharedViewModel.user.value?.identificationType,
                                    "identificationNumber" to sharedViewModel.user.value?.identificationNumber,
                                    "isBorrower" to sharedViewModel.user.value?.isBorrower,
                                )).addOnCompleteListener {
                                    _loading.value = false
                                    sharedViewModel.setUserLoggedIn(true)
                                }
                        } else {
                            Log.w("phoneBook", "signInWithCredential:failure", task.exception)
                        }

                    }
            }
            catch(e :Exception)
            {
                e.printStackTrace()
            }
        }
    }

}