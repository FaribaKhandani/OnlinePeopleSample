package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.model.UserResponse
import com.example.myapplication.repository.CustomersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(private val customersRepository: CustomersRepository) :
    ViewModel() {


    private val _userList = MutableLiveData<UserResponse>()
    val userList: LiveData<UserResponse> = _userList


    //Get All users with Hilt and livedata

    fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val users = customersRepository.getUsers()
                if (users.isSuccessful) {
                    _userList.postValue(users.body())
                } else {

                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}

