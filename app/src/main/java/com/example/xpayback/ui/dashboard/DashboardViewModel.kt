package com.example.xpayback.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xpayback.models.UserData
import com.example.xpayback.network.RestApiImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DashboardViewModel : ViewModel() {

  val loading = MutableLiveData<Boolean>()
  val errorMessage = MutableLiveData<String>()
  val userData = MutableLiveData<UserData>()

  fun getUserData(userId: Int) {
    viewModelScope.launch {
      val response = RestApiImpl.getUserData(userId)
      withContext(Dispatchers.Main) {
        if (response.isSuccessful) {
          userData.postValue(response.body())
          loading.value = false
        } else {
          onError("Error ${response.message()}")
        }
      }
    }
  }



  private fun onError(message: String) {
    errorMessage.value = message
    loading.value = false
  }
}
