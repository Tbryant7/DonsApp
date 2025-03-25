//package com.example.heroapp.model
//
//import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.heroapp.api.ApiInterface
//import com.example.heroapp.api.Hero
//import kotlinx.coroutines.launch
//import retrofit2.Response
//
//
//class HeroViewModel : ViewModel(){
//    private val heroApi = ApiInterface.create()
//    val _heroResult = MutableLiveData<Response<ArrayList<Hero>>>()
//    val heroResult : LiveData<Response<ArrayList<Hero>>> = _heroResult
//
//
//
//    fun getData(){
//       //_heroResult.value = NetworkResponse.Loading
//        viewModelScope.launch {
//            try{
//                val response = heroApi.getHeros()
//                if(response.isSuccessful){
//
//                    Log.d("API response: ", response.body().toString())
//                    _heroResult.value = response
//
//
//                }else{
//               //     _heroResult.value = NetworkResponse.Error("Failed to load data")
//                    Log.d("network error","Failed to load data")
//                }
//            }
//            catch (e : Exception){
//            //    _heroResult.value = NetworkResponse.Error("Failed to load data")
//                e.message?.let { Log.d("network error", it) }
//            }
//
//        }
//    }
//}