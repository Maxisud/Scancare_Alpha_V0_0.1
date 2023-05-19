package com.example.scancarev001

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class MainViewModel : ViewModel() {

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _penyakit = MutableLiveData<String>()
    val penyakit: LiveData<String> = _penyakit

    internal val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun uploadImage(imageMultipart: MultipartBody.Part) {
        _isLoading.value = true
        val apiService = ApiConfig.getApiService()
        val uploadImageRequest = apiService.uploadImage(imageMultipart)
        uploadImageRequest.enqueue(object : Callback<ResponsePhoto> {
            override fun onResponse(
                call: Call<ResponsePhoto>,
                response: Response<ResponsePhoto>
            ) {
                if (response.isSuccessful) {

                    _isLoading.value = false
                    _toastMessage.postValue("Image uploaded successfully")
                    val responseBody = response.body()
                    val url = URL(responseBody?.status?.data)
                    val penyakit = url.path.removePrefix("/").replace("%20", " ")
                    _penyakit.value = penyakit
                    Log.d(TAG ,"penyakitnya ${_penyakit.value}")
                } else {
                    _isLoading.value = false
                    _toastMessage.postValue("Image upload failed: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponsePhoto>, t: Throwable) {
                _isLoading.value = false
                _toastMessage.postValue("Image upload failed: ${t.message ?: "Unknown error"}")
            }
        })
    }
}