package com.devrachit.krishi.domain.models

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class SharedViewModel @Inject constructor(){
    private val _language = MutableStateFlow("English")
    var language  = _language.asStateFlow()

    fun setLanguage(language: String){
        _language.value = language
        Log.d("language",language)
    }
    fun getLanguage(): String{
        return language.value
    }

}

