package com.devrachit.krishi.presentation.authScreens.splashScreen

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.domain.models.userModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth : FirebaseAuth,
    val storage : FirebaseStorage,
    val db: FirebaseFirestore,
) : ViewModel(){

    private val _selected= MutableStateFlow(0)
    val selected = _selected.asStateFlow()

    init{
        println("uid${auth.currentUser?.uid}")
    }

    fun getUserData()
    {
        println("getUserDataCalled")
        viewModelScope.launch {
            delay(1000)
            try{
                if(auth.currentUser==null)
                {
                    setSelected(1)
                    println("User is null")
                }
                else{
                    println("User is not null")
                    val uid = auth.currentUser?.uid.toString()
                    Log.d("uid",uid)
                    db.collection("users").document(uid).get().addOnSuccessListener {document ->
                        if(!document.exists()){
                            setSelected(1)
                        }
                        else{
                            println(document.getString("name"))
                            println(document.getString("phoneNumber"))
                            println(document.getString("tempAddress"))
                            println(document.getString("permAddress"))
                            println(document.getString("identificationNumber"))
                            println(document.getString("identificationType"))
                            println(document.getBoolean("isBorrower"))
                            val userData= userModel(name=document.getString("name")!!,
                                number=document.getString("phoneNumber")!!,
                                tempAddress=document.getString("tempAddress")!!,
                                permAddress=document.getString("permAddress")!!,
                                identificationNumber=document.getString("identificationNumber")!!,
                                identificationType=document.getString("identificationType")!!,
                                isBorrower = document.getBoolean("isBorrower")!!)
                            sharedViewModel.setUser(userData)
                            setSelected(2)
                        }
                    }

                }
            }
            catch(e:Exception){
                e.printStackTrace()
                setSelected(1)
            }
        }

    }
    fun setSelected(value: Int){
        _selected.value = value
    }
    fun getSelected(): Int{
        return selected.value
    }
}