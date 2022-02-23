package hu.ait.andwallet

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.ait.andwallet.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {

    lateinit var binding: ActivitySummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var inc = intent.getIntExtra("KEY_INCOME", 0)
        var exp = intent.getIntExtra("KEY_EXPENSE", 0)

        binding.incomeSummary.text = getString(R.string.currency) + inc.toString()
        binding.expenseSummary.text = getString(R.string.currency) + exp.toString()
        binding.balance.text = getString(R.string.currency) + (inc-exp).toString()


    }

}