package com.devrachit.krishi.presentation.dashboardScreens.borrowedProductsScreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import com.devrachit.krishi.domain.models.SharedViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class BorrowedProductsViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth: FirebaseAuth,
    val storage: FirebaseStorage,
    val db: FirebaseFirestore,
): ViewModel() {

    val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    val _dataFetch = MutableStateFlow(false)
    val dataFetch = _dataFetch.asStateFlow()
}