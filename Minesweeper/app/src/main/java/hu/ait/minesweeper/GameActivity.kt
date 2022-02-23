package hu.ait.minesweeper

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import hu.ait.minesweeper.databinding.ActivityGameBinding
import hu.ait.minesweeper.model.MinesweeperBoard
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.GridLayout.spec
import com.google.android.material.snackbar.Snackbar

class GameActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener {

    lateinit var clock: Chronometer
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showTextMessage(MinesweeperBoard.mines.toString())
        clock = binding.timeTaken as Chronometer

        binding.btnReset.setOnClickListener {
            binding.minesweeperView.resetGame()
        }
        binding.toggleFlagMode.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
        MinesweeperBoard.isFlagMode = p1
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

    fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    fun showTextMessage(msg: String) {
        binding.tvMines.text = msg
    }
}