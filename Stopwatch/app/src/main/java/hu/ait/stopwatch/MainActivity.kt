package hu.ait.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Chronometer
import com.google.android.material.snackbar.Snackbar
import hu.ait.stopwatch.adapter.StopwatchAdapter
import hu.ait.stopwatch.data.Time
import hu.ait.stopwatch.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(){

    lateinit var binding: ActivityMainBinding
    lateinit var clock: Chronometer
    var hasStarted = false

    private lateinit var adapter: StopwatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        clock = binding.timer as Chronometer
        setContentView(binding.root)
        adapter = StopwatchAdapter(this)
        binding.recylerTime.adapter = adapter
        binding.btnStartStop.setOnClickListener {
            if (hasStarted){
                binding.btnStartStop.text = "Start"
                hasStarted = false
                stopTimer()
            } else {
                binding.btnStartStop.text = "Stop"
                hasStarted = true
                startTimer()
            }
        }

        binding.btnReset.setOnClickListener {
            stopTimer()
            resetTimer()
            binding.btnStartStop.text = "Start"
            hasStarted = false
            adapter.clearTime()
        }

        binding.btnMark.setOnClickListener {
            val newTime = Time(
                binding.timer.getText().toString()
            )
            timeCreated(newTime)
        }
    }

    fun startTimer() {
        clock.start()
    }

    fun stopTimer() {
        clock.stop()
    }

    fun resetTimer() {
        clock.setBase(SystemClock.elapsedRealtime());
    }

    fun timeCreated(time: Time) {
        adapter.addTime(time)

        Snackbar.make(binding.root, "Time Marked", Snackbar.LENGTH_LONG).setAction("Undo") {
            adapter.deleteTime(adapter.timeList.lastIndex)
        }.show()
    }
}