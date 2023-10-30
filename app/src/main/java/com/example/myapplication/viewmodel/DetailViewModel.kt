package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.User
import com.example.myapplication.repository.CustomersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val customersRepository: CustomersRepository) :
    ViewModel() {

    private val _userDetails = MutableLiveData<User>()
    val userDetails: LiveData<User> = _userDetails


    //Fetch specific user based on userId

    fun fetchUsersDetails(userId: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val userDetails = customersRepository.getUsersDetail(userId)
                if (userDetails.isSuccessful) {
                    _userDetails.postValue(userDetails.body())
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}