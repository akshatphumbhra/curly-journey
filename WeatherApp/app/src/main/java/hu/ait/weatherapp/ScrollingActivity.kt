package hu.ait.weatherapp

import android.os.Bundle
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import hu.ait.weatherapp.adapter.CityAdapter
import hu.ait.weatherapp.databinding.ActivityScrollingBinding

class ScrollingActivity : AppCompatActivity(), WeatherDialog.WeatherHandler {

    lateinit var cityAdapter: CityAdapter
    lateinit var binding: ActivityScrollingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScrollingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            WeatherDialog().show(supportFragmentManager, "Dialog")
        }

        initRecyclerView()
    }

    fun initRecyclerView() {
        var cities = listOf<String>(getString(R.string.budapest))
        cityAdapter = CityAdapter(this, cities)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        binding.recyclerCities.layoutManager = layoutManager
        binding.recyclerCities.adapter = cityAdapter
    }

    override fun addCity(city: String) {
        cityAdapter.addCity(city)
    }

}