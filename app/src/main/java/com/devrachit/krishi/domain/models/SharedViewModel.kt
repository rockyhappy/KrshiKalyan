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

}

