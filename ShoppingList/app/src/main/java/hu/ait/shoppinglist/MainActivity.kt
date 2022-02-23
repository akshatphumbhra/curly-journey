package hu.ait.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import hu.ait.shoppinglist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)
        anim.setAnimationListener(object: Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {

            }

            override fun onAnimationEnd(p0: Animation?) {
                var intentScrolling = Intent(this@MainActivity,
                    ScrollingActivity::class.java)

                startActivity(intentScrolling)
            }

            override fun onAnimationRepeat(p0: Animation?) {

            }

        })

        val shopping_image = binding.shoppingImg
        shopping_image.startAnimation(anim)
    }
}