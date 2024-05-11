package com.devrachit.krishi.domain.models

data class userModel(
    val name: String,
    val number: String,
    val tempAddress : String,
    val permAddress : String,
    val identificationType : String,
    val identificationNumber : String,
    val isBorrower : Boolean,
)
