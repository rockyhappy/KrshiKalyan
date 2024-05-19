package com.devrachit.krishi.presentation.dashboardScreens.addProduct

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    val sharedViewModel: SharedViewModel,
    val dataStore: DataStore<Preferences>,
    val auth: FirebaseAuth,
    val storage: FirebaseStorage,
    val db: FirebaseFirestore,
) : ViewModel(){

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl = _imageUrl.asStateFlow()

    fun getImageUrl(): String? {
        return imageUrl.value
    }

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _dataFetch = MutableStateFlow(false)
    val dataFetch = _dataFetch.asStateFlow()

    fun setDataFetch(value: Boolean) {
        _dataFetch.value = value
    }

    val nameValid = mutableStateOf(true)
    val numberValid = mutableStateOf(true)
    fun uploadProductImage(uri: Uri) {
        val userId = auth.currentUser?.uid ?: return
        val storageRef = storage.reference.child("product_images/${userId}/${uri.lastPathSegment}")
        viewModelScope.launch {
            try{
                _loading.value = true
                storageRef.putFile(uri)
                    .addOnSuccessListener { taskSnapshot ->
                        storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                            _imageUrl.value = downloadUri.toString()
                        }
                    }
                    .addOnFailureListener { exception ->
                        exception.printStackTrace()
                    }
                    .addOnCompleteListener{
                        _loading.value = false
                    }
            }
            catch(e:Exception){
                e.printStackTrace()
            }
        }
    }


    fun addItem(price: String, imageUrl: String, name: String)
    {
        var item= itemModel(
            imageUrl = imageUrl,
            name = name,
            ownerName = sharedViewModel.getUser().name,
            ownerUid = auth.currentUser?.uid!!,
            price = price,
            borrowerUid = "null",
            rating = "4.5",
        )

        viewModelScope.launch {
            try {
                _loading.value = true
                val uniqueId = db.collection("itemRequest").document().id
                item.uid = uniqueId
                db.collection("items").document(uniqueId).set(item)
                    .addOnSuccessListener { documentReference ->
                        Log.d("MainScreen", "DocumentSnapshot added with ID: ${documentReference}")
                        sharedViewModel.addSelfUploads(item)
                    }
                    .addOnFailureListener { e ->
                        Log.w("MainScreen", "Error adding document", e)
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
}