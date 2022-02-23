package hu.ait.minesweeper

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Chronometer
import hu.ait.minesweeper.databinding.ActivityMainBinding
import hu.ait.minesweeper.model.MinesweeperBoard

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    companion object {
        const val KEY_SIZE = "KEY_SIZE"
        const val KEY_MINES = "KEY_MINES"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnEasy.setOnClickListener {
            val intentGame = Intent()
            intentGame.setClass(this, GameActivity::class.java)
            MinesweeperBoard.setDifficulty(5, 3)
            startActivity(intentGame)
        }

        binding.btnHard.setOnClickListener {
            val intentGame = Intent()
            intentGame.setClass(this, GameActivity::class.java)
            MinesweeperBoard.setDifficulty(10, 10)
            startActivity(intentGame)
        }


    }
}