package com.example.myapplication.model


data class UserResponse(
    val customers: List<User>
)

data class User(

    val id: Int,
    val imageUrl: String,
    val currentLatitude: Double,
    val currentLongitude: Double,
    val firstName: String,
    val lastName: String,
    val stickers: List<String>,
    val gender: String,
    val phoneNumber: String,
    val address: Address
)

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zip: String,
    val country: String
)
