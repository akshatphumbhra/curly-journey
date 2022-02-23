package hu.ait.andwallet

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import hu.ait.andwallet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private var IncOrExp = true
    private var incomeAmt = 0
    private var expenseAmt = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.btnSave.setOnClickListener{
            insertTodo()
        }

        binding.btnIncExp.setOnCheckedChangeListener { _, isChecked ->
            IncOrExp = isChecked
        }

        binding.btnClear.setOnClickListener{
            binding.layoutContent.removeAllViews()
            incomeAmt = 0
            expenseAmt = 0
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_summary -> {
                var intentStart = Intent()
                intentStart.setClass(this@MainActivity, SummaryActivity::class.java)

                intentStart.putExtra("KEY_INCOME", incomeAmt)
                intentStart.putExtra("KEY_EXPENSE", expenseAmt)
                startActivity(intentStart)
            }

        }
        return true
    }

    override fun onBackPressed() {
        val intentMain = Intent(Intent.ACTION_MAIN)
        intentMain.addCategory(Intent.CATEGORY_HOME)
        intentMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intentMain)
    }

    private fun insertTodo() {

        if(binding.etItem.text.isEmpty() && binding.etAmt.text.isEmpty()){
            binding.etItem.error = getString(R.string.empty_val)
            binding.etAmt.error = getString(R.string.empty_val)
        } else if(binding.etItem.text.isEmpty()){
            binding.etItem.error = getString(R.string.empty_val)
        } else if(binding.etAmt.text.isEmpty()){
            binding.etAmt.error = getString(R.string.empty_val)
        } else {
            lateinit var layoutDetails: View

            if (IncOrExp) {
                layoutDetails = layoutInflater.inflate(
                    R.layout.income_row, null
                )

                incomeAmt += binding.etAmt.text.toString().toInt()
                layoutDetails.findViewById<TextView>(R.id.tvAmt).text = binding.etAmt.text.toString() + getString(R.string.currency)

            } else {
                layoutDetails = layoutInflater.inflate(
                    R.layout.expense_row, null
                )

                expenseAmt += binding.etAmt.text.toString().toInt()
                layoutDetails.findViewById<TextView>(R.id.tvAmt).text = getString(
                    R.string.minus) + binding.etAmt.text.toString() + getString(R.string.currency)
            }

            layoutDetails.findViewById<Button>(R.id.btnDelete).setOnClickListener{
                if (layoutDetails.findViewById<TextView>(R.id.tvAmt).text.toString().substring(0,1).equals(getString(
                                        R.string.minus))) {
                    var s = layoutDetails.findViewById<TextView>(R.id.tvAmt).text.toString()
                    expenseAmt -= s.substring(1, s.length-1).toInt()
                } else {
                        var s = layoutDetails.findViewById<TextView>(R.id.tvAmt).text.toString()
                    incomeAmt -= s.substring(0, s.length-1).toInt()
                }
                binding.layoutContent.removeView(layoutDetails)
            }

            layoutDetails.findViewById<TextView>(R.id.tvItem).text = binding.etItem.text

            binding.etItem.setText("")
            binding.etAmt.setText("")

            binding.layoutContent.addView(layoutDetails, 0)
        }

    }

}
