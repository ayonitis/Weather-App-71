package com.creativeinstitute.weatherapp71

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.creativeinstitute.weatherapp71.api.Constant
import com.creativeinstitute.weatherapp71.api.RetrofitInstance
import com.creativeinstitute.weatherapp71.api.weatherModel
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel() {

    private val weatherAPI = RetrofitInstance.weatherAPI
    private val _weatherResult = MutableLiveData<NetworkResponse<weatherModel>>()
    val weatherResult : LiveData<NetworkResponse<weatherModel>> = _weatherResult

    fun getData(city: String){
        viewModelScope.launch {

            _weatherResult.value = NetworkResponse.Loading

            try {
                val response = weatherAPI.getWeather(Constant.apiKey, city)
                if(response.isSuccessful){
                    response.body()?.let{
                        _weatherResult.value = NetworkResponse.Success(it)
                    }
                }else {
                    _weatherResult.value = NetworkResponse.Error("Failed to load data!")
                }
            }
            catch (e : Exception){_weatherResult.value = NetworkResponse.Error("Failed to load data!")}

        }

    }

}