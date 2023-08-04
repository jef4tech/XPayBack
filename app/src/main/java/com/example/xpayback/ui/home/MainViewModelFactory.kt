package com.example.xpayback.ui.home
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.xpayback.network.RetrofitClientFactory

/**
 * @author jeffin
 * @date 31/01/23
 */
class MainViewModelFactory(private val retrofitClientFactory: RetrofitClientFactory):
    ViewModelProvider.Factory {

override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(HomeViewModel::class.java)){
        return HomeViewModel(retrofitClientFactory) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
}
}
