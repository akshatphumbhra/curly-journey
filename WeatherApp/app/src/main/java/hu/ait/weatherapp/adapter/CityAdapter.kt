package hu.ait.weatherapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.ait.weatherapp.R
import hu.ait.weatherapp.WeatherActivity
import hu.ait.weatherapp.databinding.CityRowBinding

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>{

    var cities = mutableListOf<String>()

    val context: Context
    constructor(context: Context, cityList: List<String>) : super() {
        this.context = context

        cities.addAll(cityList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityAdapter.ViewHolder {
        val weatherBinding = CityRowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(weatherBinding)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    fun addCity(city: String){
        cities.add(city)
        notifyItemInserted(cities.lastIndex)
    }

    override fun onBindViewHolder(holder: CityAdapter.ViewHolder, position: Int) {
        var city = cities.get(holder.adapterPosition)

        holder.tvCityName.text = city

        holder.btnWeatherDetails.setOnClickListener{
            var intent = Intent(context, WeatherActivity::class.java)
            intent.putExtra("city name", city)
            context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener{
            removeCity(holder.adapterPosition)
        }
    }

    fun removeCity(index: Int) {
        cities.removeAt(index)
        notifyItemRemoved(index)
    }

    inner class ViewHolder(val weatherBinding: CityRowBinding) : RecyclerView.ViewHolder(weatherBinding.root) {
        val tvCityName = weatherBinding.tvCityName
        val btnWeatherDetails = weatherBinding.btnWeatherDetails
        val btnDelete = weatherBinding.btnDelete
    }

}