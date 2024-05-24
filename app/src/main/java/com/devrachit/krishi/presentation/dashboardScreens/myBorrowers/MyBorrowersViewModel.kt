package com.devrachit.krishi.presentation.dashboardScreens.myBorrowers

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.domain.models.itemModel
import com.devrachit.krishi.domain.models.itemModel2
import com.devrachit.krishi.domain.models.userModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBorrowersViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth: FirebaseAuth,
    val storage: FirebaseStorage,
    val db: FirebaseFirestore,
): ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _dataFetch = MutableStateFlow(false)
    val dataFetch = _dataFetch.asStateFlow()

    fun setDataFetch(value: Boolean) {
        _dataFetch.value = value
    }
    fun deleteItem(item: itemModel2) {
        viewModelScope.launch {
            try {
                _loading.value = true
                db.collection("items")
                    .whereEqualTo("ownerUid", auth.currentUser?.uid)
                    .whereEqualTo("name", item.name)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot.documents) {
                            db.collection("items").document(document.id).delete()
                                .addOnSuccessListener {
                                    Log.d("MainScreen", "DocumentSnapshot successfully deleted!")
                                    sharedViewModel.deleteSelfUploads2(item)
                                }
                                .addOnFailureListener { e ->
                                    Log.w("MainScreen", "Error deleting document", e)
                                }
                        }
                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                    }
                    .addOnCompleteListener {
                        _loading.value = false
                        _dataFetch.value = true
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun fetchBorrowerDetails(itemModel:itemModel2, onResult: (userModel) -> Unit)
    {
        viewModelScope.launch {
            try {
                db.collection("users").document(itemModel.borrowerUid).get().addOnSuccessListener { document ->
                    if (document.exists()) {
                        val userData = userModel(
                            name = document.getString("name")!!,
                            number = document.getString("phoneNumber")!!,
                            tempAddress = document.getString("tempAddress")!!,
                            permAddress = document.getString("permAddress")!!,
                            identificationNumber = document.getString("identificationNumber")!!,
                            identificationType = document.getString("identificationType")!!,
                            isBorrower = document.getBoolean("isBorrower")!!,
                        )
                        onResult(userData)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}