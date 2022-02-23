package hu.ait.weatherapp.network

import hu.ait.weatherapp.data.Base
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CityAPI {

    @GET("data/2.5/weather")
    fun getWeather(@Query("q") city: String,
                   @Query("units") units: String,
                   @Query("appid") appid: String): Call<Base>
}