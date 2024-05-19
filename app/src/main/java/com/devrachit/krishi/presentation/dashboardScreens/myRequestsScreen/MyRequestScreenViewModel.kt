package com.devrachit.krishi.presentation.dashboardScreens.myRequestsScreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.domain.models.itemModel
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
class MyRequestScreenViewModel @Inject constructor(
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

    private val _borrowerDetails =MutableStateFlow(userModel("","","","","","",true))
    val borrowerDetails = _borrowerDetails.asStateFlow()

    fun getMyRequests() {
        viewModelScope.launch {
            try{
                _loading.value = true
                db.collection("itemRequest")
                    .whereEqualTo("ownerUid", auth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val requests = mutableListOf<itemModel>()
                        for (document in querySnapshot.documents) {
                            var itemData = itemModel(
                                imageUrl = document.getString("imageUrl")!!,
                                name = document.getString("name")!!,
                                ownerName = document.getString("ownerName")!!,
                                ownerUid = document.getString("ownerUid")!!,
                                price = document.getString("price")!!,
                                borrowerUid = document.getString("borrowerUid")!!,
                                rating = document.getString("rating")!!,
                                uid= document.getString("uid")!!
                            )
                            requests.add(itemData)
                        }
                        sharedViewModel.setBorrowerRequests(requests)
                        println("Requests: $requests")
                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                    }
                    .addOnCompleteListener {
                        _loading.value = false
                    }
            }
            catch(e:Exception) {
                e.printStackTrace()
            }
        }
    }
    fun fetchBorrowerDetails(itemModel:itemModel, onResult: (userModel) -> Unit)
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
                            isBorrower = document.getBoolean("isBorrower")!!
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