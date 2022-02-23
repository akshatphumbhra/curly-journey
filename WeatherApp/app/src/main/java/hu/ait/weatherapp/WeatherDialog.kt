package hu.ait.weatherapp

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import hu.ait.weatherapp.data.Base
import hu.ait.weatherapp.databinding.WeatherDialogBinding
import java.lang.RuntimeException
import java.util.*

class WeatherDialog : DialogFragment(){

    public interface WeatherHandler {
        fun addCity(city: String)
    }

    lateinit var weatherHandler: WeatherHandler

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is WeatherHandler) {
            weatherHandler = context
        } else {
            throw RuntimeException(getString(R.string.exception))
        }
    }

    lateinit var weatherDialogBinding: WeatherDialogBinding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.add_city))

        weatherDialogBinding = WeatherDialogBinding.inflate(layoutInflater)
        builder.setView(weatherDialogBinding.root)

        builder.setPositiveButton(getString(R.string.save)) {
                dialog, which ->

            val newCity = weatherDialogBinding.etCityText.text.toString()
            weatherHandler.addCity(newCity)
        }

        builder.setNegativeButton(getString(R.string.cancel)) {
                dialog, which ->
        }

        return builder.create()
    }

}