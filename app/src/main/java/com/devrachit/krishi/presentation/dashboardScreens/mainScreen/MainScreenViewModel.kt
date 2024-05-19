package com.devrachit.krishi.presentation.dashboardScreens.mainScreen

import android.util.Log
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
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


    fun logout() {
        auth.signOut()
        sharedViewModel.setUserLoggedIn(false)
        sharedViewModel.setUser(userModel("", "", "", "", "", "", true))
    }
    fun getSelfUploads() {
        viewModelScope.launch {
            try {
                _loading.value = true
                db.collection("items")
                    .whereEqualTo("ownerUid", auth.currentUser?.uid)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val uploads = mutableListOf<itemModel>()
                        val uploads2 = mutableListOf<itemModel>()
                        for (document in querySnapshot.documents) {
                            var itemData = itemModel(
                                imageUrl = document.getString("imageUrl")!!,
                                name = document.getString("name")!!,
                                ownerName = document.getString("ownerName")!!,
                                ownerUid = document.getString("ownerUid")!!,
                                price = document.getString("price")!!,
                                borrowerUid = document.getString("borrowerUid")!!,
                                rating = document.getString("rating")!!,
                                uid= document.id
                            )
                            if(document.getString("borrowerUid") == "null"){
                                uploads.add(itemData)
                            }
                            else{
                                uploads2.add(itemData)
                            }
                            sharedViewModel.setSelfUploads(uploads)
                            sharedViewModel.setSelfUploads2(uploads2)
                            println("Item added ${itemData}")
                        }
                        println("Uploads $uploads")
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

    fun deleteItem(item: itemModel) {
        viewModelScope.launch {
            try {

                _loading.value = true
                db.collection("items")
                    .whereEqualTo("uid",item.uid)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        for (document in querySnapshot.documents) {
                            db.collection("items").document(document.id).delete()
                                .addOnSuccessListener {
                                    Log.d("MainScreen", "DocumentSnapshot successfully deleted!")
                                    sharedViewModel.deleteSelfUploads(item)
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

    fun addItem(itemModel:itemModel)
    {
        val item=itemModel(
            imageUrl = itemModel.imageUrl,
            name = itemModel.name,
            ownerName = itemModel.ownerName,
            ownerUid = itemModel.ownerUid,
            price = itemModel.price,
            borrowerUid = itemModel.borrowerUid,
            rating = itemModel.rating,
        )

        viewModelScope.launch {
            try {
                _loading.value = true
                db.collection("items")
                    .add(item)
                    .addOnSuccessListener { documentReference ->
                        Log.d("MainScreen", "DocumentSnapshot added with ID: ${documentReference.id}")
                        sharedViewModel.addSelfUploads(item)
                    }
                    .addOnFailureListener { e ->
                        Log.w("MainScreen", "Error adding document", e)
                    }
                    .addOnCompleteListener {
                        _loading.value = false
//                        _dataFetch.value = true
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}