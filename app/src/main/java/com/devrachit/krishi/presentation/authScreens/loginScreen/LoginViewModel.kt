package com.devrachit.krishi.presentation.authScreens.loginScreen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.common.util.getActivity
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.domain.models.userModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
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

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    fun sendOTP(context: Context, phoneNumber: String)
    {
        viewModelScope.launch {
            try {
                _loading.value=true
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
                _loading.value=false
            }
            catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun verifyOTP(context : Context,otp: String) {
        var borrower=false
        viewModelScope.launch {
            try {
                _loading.value=true
                val credential = PhoneAuthProvider.getCredential(verificationToken.value, otp)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("phoneBook", "signInWithCredential:success")

                            val uid=auth.currentUser?.uid
                            if (uid != null) {
                                db.collection("users").document(uid).get()
                                    .addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            sharedViewModel.setUserLoggedIn(true)
                                            val userData= userModel(name=document.getString("name")!!,
                                                number=document.getString("phoneNumber")!!,
                                                tempAddress=document.getString("tempAddress")!!,
                                                permAddress=document.getString("permAddress")!!,
                                                identificationNumber=document.getString("identificationNumber")!!,
                                                identificationType=document.getString("identificationType")!!,
                                                isBorrower = document.getBoolean("isBorrower")!!)
                                            sharedViewModel.setUser(userData)
                                            borrower=sharedViewModel.getUser().isBorrower
                                            println("DocumentSnapshot data: ${document.data}")
                                        } else {
                                            Log.d("phoneBook", "No such document")
                                            auth.signOut()
                                            Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    .addOnFailureListener { exception ->
                                        Log.d("phoneBook", "get failed with ", exception)
                                    }
                            }
                            else{
                                Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                            }

                        } else {
                            Log.d("phoneBook", "signInWithCredential:failure", task.exception)
                        }
                        _loading.value=false
                    }
                save("borrower",borrower.toString())
            }
            catch(e: Exception) {
                e.printStackTrace()
            }
        }

    }
    private suspend fun save (key:String , value:String){
        val dataStoreKey= preferencesKey<String>(key)
        dataStore.edit{temp ->
            temp[dataStoreKey]=value
        }
    }
}