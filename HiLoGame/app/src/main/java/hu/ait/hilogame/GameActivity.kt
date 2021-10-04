package hu.ait.hilogame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import hu.ait.hilogame.databinding.ActivityGameBinding
import java.util.*


class GameActivity : AppCompatActivity() {

    var generatedNum = -1
    lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if ((savedInstanceState != null) && savedInstanceState.containsKey("KEY_GEN")){
            generatedNum = savedInstanceState.getInt("KEY_GEN")
        } else {
            generateRandomNumber()
        }

        binding.btnGuess.setOnClickListener {
            val myNumber = binding.etData.text.toString().toInt()

            if (myNumber < generatedNum) {
                binding.tvGuess.text = "Your guess was too low!"
            } else if (myNumber > generatedNum) {
                binding.tvGuess.text = "Your guess was too high!"
            } else {
                binding.tvGuess.text = "Correct! You win!"
                startActivity(Intent(this, ResultActivity::class.java))
            }

            binding.etData.getText().clear()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("KEY_GEN", generatedNum)
    }

    fun generateRandomNumber() {
        val rand = Random(System.currentTimeMillis())
        generatedNum = rand.nextInt(10)
    }
}