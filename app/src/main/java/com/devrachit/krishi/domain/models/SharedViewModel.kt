package com.devrachit.krishi.domain.models

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModel
import com.devrachit.krishi.datastore.readFromDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Singleton
class SharedViewModel @Inject constructor(){


    private val _language = MutableStateFlow("English")
    var language  = _language.asStateFlow()

    fun setLanguage(language: String){
        _language.value = language
        Log.d("language",_language.value)
    }
    fun getLanguage(): String{
        return language.value
    }


    private val _user= MutableStateFlow<userModel>(userModel("","","","","","",true))
    var user = _user.asStateFlow()



    fun setUser(user: userModel){
        _user.value = user
    }

    fun getUser(): userModel{
        return user.value
    }


    private val _isUserLoggedIn = MutableStateFlow(false)
    var isUserLoggedIn = _isUserLoggedIn.asStateFlow()

    fun setUserLoggedIn(value: Boolean){
        _isUserLoggedIn.value = value
    }

    fun getUserLoggedIn(): Boolean{
        return isUserLoggedIn.value
    }

    private val _selfUploads = MutableStateFlow<List<itemModel>>(emptyList())
    var selfUploads = _selfUploads.asStateFlow()

    fun setSelfUploads(uploads: List<itemModel>){
        _selfUploads.value = uploads
    }
    fun deleteSelfUploads(itemModel: itemModel){
        val list = selfUploads.value.toMutableList()
        list.remove(itemModel)
        _selfUploads.value = list
    }

    fun addSelfUploads(itemModel: itemModel){
        val list = selfUploads.value.toMutableList()
        list.add(itemModel)
        _selfUploads.value = list
    }
    fun getSelfUploads(): List<itemModel>{
        return selfUploads.value
    }

    private val _selfUploads2 = MutableStateFlow<List<itemModel>>(emptyList())
    var selfUploads2 = _selfUploads2.asStateFlow()

    fun setSelfUploads2(uploads: List<itemModel>){
        _selfUploads2.value = uploads
    }
    fun deleteSelfUploads2(itemModel: itemModel){
        val list = selfUploads2.value.toMutableList()
        list.remove(itemModel)
        _selfUploads2.value = list
    }

    fun addSelfUploads2(itemModel: itemModel){
        val list = selfUploads2.value.toMutableList()
        list.add(itemModel)
        _selfUploads2.value = list
    }
    fun getSelfUploads2(): List<itemModel>{
        return selfUploads2.value
    }


    val _borrowerDetails = MutableStateFlow<userModel>(userModel("","","","","","",true))
    var borrowerDetails = _borrowerDetails.asStateFlow()

    fun setBorrowerDetails(user: userModel){
        _borrowerDetails.value = user
    }

    fun getBorrowerDetails(): userModel{
        return borrowerDetails.value
    }


    private val _borrowerMadeRequests = MutableStateFlow<List<itemModel>>(emptyList())
    var borrowerMadeRequest = _borrowerMadeRequests.asStateFlow()

    fun setBorrowerMadeRequests(requests: List<itemModel>){
        _borrowerMadeRequests.value = requests
    }

    fun getBorrowerMadeRequests(): List<itemModel>{
        return borrowerMadeRequest.value
    }

    private val _borrowerRequests = MutableStateFlow<List<itemModel>>(emptyList())
    var borrowerRequests = _borrowerRequests.asStateFlow()

    fun setBorrowerRequests(requests: List<itemModel>){
        _borrowerRequests.value = requests
    }

}

