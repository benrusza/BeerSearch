package com.brz.beersearch.vm

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brz.beersearch.model.ResponseBeersItem
import com.brz.beersearch.retrofit.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class BeersViewModel : ViewModel() {
    private val mResponse = MutableLiveData<List<ResponseBeersItem>>()
    var beers: LiveData<List<ResponseBeersItem>> = mResponse
    var isLoading = MutableLiveData<Boolean>()
    var isLoaded = MutableLiveData<Boolean>()

    var errorMessage: String by mutableStateOf("")

    init {
        isLoading.value = false
        isLoaded.value = false
    }

    fun getBeers(beerName: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Client.service.getBeers(beerName).execute()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        isLoading.value = false
                        mResponse.value = response.body()!!
                        beers = mResponse
                    }

                }else{
                    errorMessage = response.code().toString()
                }


            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = e.message.toString()
            }
        }
    }

    fun getBeersById(id: String) {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = Client.service.getBeersById(id).execute()
                if(response.isSuccessful){
                    withContext(Dispatchers.Main){
                        isLoading.value = false
                        mResponse.value = response.body()!!
                        beers = mResponse
                        isLoaded.value = true
                    }

                }else{
                    errorMessage = response.code().toString()
                }


            } catch (e: Exception) {
                e.printStackTrace()
                errorMessage = e.message.toString()
            }
        }
    }
}