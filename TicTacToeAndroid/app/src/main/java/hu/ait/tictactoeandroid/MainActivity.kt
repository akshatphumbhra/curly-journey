package hu.ait.tictactoeandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import hu.ait.tictactoeandroid.databinding.ActivityMainBinding
import android.R
import android.os.SystemClock
import android.view.View

import android.widget.Chronometer




class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var clock: Chronometer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        clock = binding.timeTaken as Chronometer
        binding.btnReset.setOnClickListener {
            binding.ticTacToeView.resetGame()
        }
    }

    fun showTextMessage(msg: String) {
        binding.tvPlayer.text = msg
    }

    fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
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
}