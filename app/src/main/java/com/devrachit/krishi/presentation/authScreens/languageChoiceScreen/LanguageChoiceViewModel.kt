package com.devrachit.krishi.presentation.authScreens.languageChoiceScreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import com.devrachit.krishi.domain.models.SharedViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LanguageChoiceViewModel @Inject constructor(

    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>
):ViewModel() {

}