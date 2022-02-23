package hu.ait.andwallet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.ait.andwallet.databinding.ActivityPinBinding

class PinActivity : AppCompatActivity() {

    lateinit var binding: ActivityPinBinding
    private val pin = 1234

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{
            when {
                binding.password.text.isEmpty() -> binding.password.error = getString(R.string.invalid_pin)
                binding.password.text.toString() == pin.toString() -> {
                    var intentStart = Intent()
                    intentStart.setClass(this@PinActivity, MainActivity::class.java)
                    startActivity(intentStart)
                }
                else -> binding.password.error = getString(R.string.incorrect_pin)
            }
        }

    }
}