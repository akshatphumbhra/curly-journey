package hu.ait.aittimedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.util.*

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    var btnShowTime = findViewById<Button>(R.id.btnShow)
    var tvData = findViewById<TextView>(R.id.tvData)


    btnShowTime.setOnClickListener {
        var time = Date(System.currentTimeMillis()).toString()

        Toast.makeText(this, time, Toast.LENGTH_LONG).show()

        tvData.text = time
    }

}