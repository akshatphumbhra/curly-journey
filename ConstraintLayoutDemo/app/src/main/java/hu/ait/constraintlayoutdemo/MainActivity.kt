package hu.ait.constraintlayoutdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import hu.ait.constraintlayoutdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdd.setOnClickListener {
            addDetailsItem()
        }
    }

    private fun addDetailsItem() {
        val layoutDetails = layoutInflater.inflate(R.layout.details_item, null)

        layoutDetails.findViewById<Button>(R.id.btnDelete).setOnClickListener {
            binding.layoutContent.removeView(layoutDetails)
        }

        binding.layoutContent.addView(layoutDetails)
    }
}