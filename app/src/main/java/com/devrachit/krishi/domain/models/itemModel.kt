package com.devrachit.krishi.domain.models

data class itemModel(
    val imageUrl: String,
    val name: String,
    val ownerName: String,
    val price: String,
    val ownerUid : String,
    val borrowerUid : String,
    val rating : String= "4.5",
)
