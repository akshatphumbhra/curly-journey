package hu.ait.threaddemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.threaddemo.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    var enabled = false
    lateinit var mainTimer: Timer

    inner class MyThread: Thread() {
        override fun run()
        {
            while (enabled) {
                runOnUiThread {
                    binding.tvData.append("#")
                }
                sleep(1000)
            }
        }
    }

    inner class MyTimerTask: TimerTask() {
        override fun run() {
            runOnUiThread {
                binding.tvData.append("W")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnStart.setOnClickListener {
            if (!enabled) {
                enabled = true
                MyThread().start()
            }

            mainTimer = Timer()
            mainTimer.schedule(MyTimerTask(), 3000, 1000)
        }

        binding.btnStop.setOnClickListener {
            enabled = false
            mainTimer.cancel()
        }
    }

    override fun onDestroy() {
        enabled = false
        try {
            mainTimer.cancel()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }
}