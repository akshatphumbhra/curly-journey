package hu.ait.simplecalculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hu.ait.simplecalculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnPlus.setOnClickListener {
            try {
                if (binding.etNumA.text.isNotEmpty()) {
                    if (binding.etNumB.text.isNotEmpty()) {
                        val numA = binding.etNumA.text.toString().toInt()
                        val numB = binding.etNumB.text.toString().toInt()

                        val result = numA + numB

                        binding.tvData.text = getString(R.string.text_result, result)
                    } else {
                        binding.etNumB.error = getString(R.string.empty_field_error)
                    }
                } else {
                    binding.etNumA.error = getString(R.string.empty_field_error)
                }

            } catch (e: Exception) {
                binding.tvData.text = "Error: ${e.message}"
            }
        }

        binding.btnMinus.setOnClickListener {
            try {
                if (binding.etNumA.text.isNotEmpty()) {
                    if (binding.etNumB.text.isNotEmpty()) {
                        val numA = binding.etNumA.text.toString().toInt()
                        val numB = binding.etNumB.text.toString().toInt()

                        val result = numA - numB

                        binding.tvData.text = getString(R.string.text_result, result)
                    } else {
                        binding.etNumB.error = getString(R.string.empty_field_error)
                    }
                } else {
                    binding.etNumA.error = getString(R.string.empty_field_error)
                }

            } catch (e: Exception) {
                binding.tvData.text = "Error: ${e.message}"
            }
        }

    }
}