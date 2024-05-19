package com.devrachit.krishi.presentation.dashboardScreens.mainScreenBorrower

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devrachit.krishi.domain.models.SharedViewModel
import com.devrachit.krishi.domain.models.itemModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class MainScreenBorrowerViewModel @Inject constructor(
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _items = MutableStateFlow<List<itemModel>>(emptyList())
    val items = _items.asStateFlow()

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(1000L) // 1 second debounce
                .distinctUntilChanged()
                .collectLatest { query ->
                    getSelfUploads(query)
                }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getSelfUploads(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loading.value = true
                db.collection("items")
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
                                uid=document.id
                            )
                            if (document.getString("borrowerUid") == "null" && itemData.name.contains(
                                    query,
                                    ignoreCase = true
                                )
                            ) {
                                uploads.add(itemData)
                            } else if (document.getString("borrowerUid") == auth.currentUser?.uid )
                            {
                                uploads2.add(itemData)
                            }
                            sharedViewModel.setSelfUploads(uploads)
                            sharedViewModel.setSelfUploads2(uploads2)
                            _items.value = uploads + uploads2
                            println("Item added ${itemData}")
                        }
                        println("Uploads1 $uploads")
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

    fun addItemToBorrow(item: itemModel) {
        viewModelScope.launch {
            try {
                _loading.value = true
                val querySnapshot = db.collection("itemRequest")
                    .whereEqualTo("uid", item.uid)
                    .get()
                    .await()
                if (querySnapshot.isEmpty) {
                    db.collection("itemRequest").add(
                        itemModel(
                            imageUrl = item.imageUrl,
                            name = item.name,
                            ownerName = item.ownerName,
                            ownerUid = item.ownerUid,
                            price = item.price,
                            borrowerUid = auth.currentUser!!.uid,
                            rating = item.rating,
                            uid = item.uid
                        )
                    )
                        .addOnSuccessListener {
                            println("Item added successfully: $it")
                        }
                        .addOnFailureListener { exception ->
                            exception.printStackTrace()
                        }
                        .addOnCompleteListener {
                            _loading.value = false
                        }
                }
                else
                {
                    _loading.value = false
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loading.value = false
            }

        }
    }
}