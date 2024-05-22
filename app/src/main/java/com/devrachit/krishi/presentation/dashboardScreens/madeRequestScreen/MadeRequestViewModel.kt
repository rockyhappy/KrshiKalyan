package com.devrachit.krishi.presentation.dashboardScreens.madeRequestScreen

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.domain.models.itemModel
import com.devrachit.krishi.domain.models.itemModel2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MadeRequestViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth: FirebaseAuth,
    val storage: FirebaseStorage,
    val db: FirebaseFirestore,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _dataFetch = MutableStateFlow(false)
    val dataFetch = _dataFetch.asStateFlow()

    fun getMyRequests() {
        _loading.value = true
        db.collection("itemRequest")
            .whereEqualTo("borrowerUid", auth.currentUser?.uid)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val requests = mutableListOf<itemModel2>()
                for (document in querySnapshot.documents) {
                    var itemData = itemModel2(
                        imageUrl = document.getString("imageUrl")!!,
                        name = document.getString("name")!!,
                        ownerName = document.getString("ownerName")!!,
                        ownerUid = document.getString("ownerUid")!!,
                        price = document.getString("price")!!,
                        borrowerUid = document.getString("borrowerUid")!!,
                        rating = document.getString("rating")!!,
                        uid= document.getString("uid")!!,
                        days = document.getString("days")!!,
                        quantity = document.getString("quantity")!!,
                    )
                    requests.add(itemData)
                }
                sharedViewModel.setBorrowerMadeRequests(requests)
                println("Requests: $requests")
            }
            .addOnFailureListener { exception ->
                exception.printStackTrace()
            }
            .addOnCompleteListener {
                _loading.value = false
            }
    }

}