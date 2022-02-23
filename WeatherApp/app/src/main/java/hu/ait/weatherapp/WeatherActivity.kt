package hu.ait.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import hu.ait.weatherapp.data.Base
import hu.ait.weatherapp.databinding.ActivityWeatherBinding
import hu.ait.weatherapp.network.CityAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherActivity : AppCompatActivity() {

    lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var name = intent.getStringExtra("city name")
        binding.tvCityName.text = name


        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherAPI = retrofit.create(CityAPI::class.java)

        val call = weatherAPI.getWeather(
            binding.tvCityName.text.toString(),
            "metric",
            "57d5b0bff53f76382133a0fb0c2ec357"
        )

        call.enqueue(
            object: Callback<Base> {
                override fun onFailure(call: Call<Base>, t: Throwable) {

                    binding.tvCityName.text = getString(R.string.error)
                }
                override fun onResponse(call: Call<Base>, response: Response<Base>) {
                    val weatherBase : Base? = response.body()
                    binding.tvLatitude.text = "(${weatherBase?.coord?.lat}°,"
                    binding.tvLongitude.text = " ${weatherBase?.coord?.lon}°)"
                    binding.tvPressure.text = getString(R.string.pressure) + "${weatherBase?.main?.pressure}" + getString(
                                            R.string.pressureUnit)
                    binding.tvTemperature.text = getString(R.string.temp) + "${weatherBase?.main?.temp}" + getString(
                                            R.string.tempUnit)
                    binding.tvDescription.text = "${weatherBase?.weather?.get(0)?.description}"
                    binding.tvWind.text = getString(R.string.wind) + "${weatherBase?.wind?.speed}" + getString(
                                            R.string.windUnit)
                    binding.tvHumidity.text = getString(R.string.humidity) +  "${weatherBase?.main?.humidity}" + getString(
                                            R.string.percent)

                    Glide.with(this@WeatherActivity).load(
                        ("https://openweathermap.org/img/w/" + response.body()?.weather?.get(0)?.icon + ".png"))
                        .into(binding.ivWeatherIconLeft)

                    Glide.with(this@WeatherActivity).load(
                        ("https://openweathermap.org/img/w/" + response.body()?.weather?.get(0)?.icon + ".png"))
                        .into(binding.ivWeatherIconRight)
                }
            }
        )


    }
}
