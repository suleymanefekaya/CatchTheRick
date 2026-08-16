package com.suleyman.catchtherick

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Looper
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.suleyman.catchtherick.databinding.ActivityMainBinding
import java.util.Random
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var score = 0
    var imageArray = ArrayList<ImageView>()
    var runnable= Runnable{}
    var handler: Handler = Handler(Looper.getMainLooper())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //ImageArray
        imageArray.add(binding.imageView)
        imageArray.add(binding.imageView2)
        imageArray.add(binding.imageView3)
        imageArray.add(binding.imageView4)
        imageArray.add(binding.imageView5)
        imageArray.add(binding.imageView6)
        imageArray.add(binding.imageView7)
        imageArray.add(binding.imageView8)
        imageArray.add(binding.imageView9)

        hideImages()

        //CountDownTimer

        object : CountDownTimer(15000,1000){
            override fun onTick(p0: Long) {
                binding.timerText.text = "Time: ${p0/1000}"
            }
            override fun onFinish() {
                binding.timerText.text = "Time: 0"
                handler.removeCallbacks(runnable)

                for (image in imageArray){
                    image.visibility = View.INVISIBLE
                }

                //alert dialog
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart The Game?")
                //restart
                alert.setPositiveButton("Yes", DialogInterface.OnClickListener{dialogInterface, i ->
                    val intentFromMain = intent
                    finish()
                    startActivity(intentFromMain)
                })


                //finish
                alert.setNegativeButton("No", DialogInterface.OnClickListener{dialogInterface, i ->
                    Toast.makeText(this@MainActivity,"Game Over!", Toast.LENGTH_LONG).show()
                })
                alert.show()


            }


        }.start()

    }

    fun hideImages(){
        runnable = object: Runnable{
            override fun run() {
                for (image in imageArray) {
                    //View.GONE, View.INVISIBLE vs View.VISIBLE
                    image.visibility = View.INVISIBLE
                }
                val random = Random()
                val randomIndex = random.nextInt(9)
                imageArray[randomIndex].visibility = View.VISIBLE

                handler.postDelayed(runnable,500)
            }
        }
        handler.post(runnable)
    }

    fun increaseScore(view: View){
        score = score + 1
        binding.scoreText.text = "Score :${score}"

    }
}