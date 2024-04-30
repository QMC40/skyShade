package com.mobsofdev.android.skyshade

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random


class SnowActivity : AppCompatActivity() {
    private var sunImageView: ImageView? = null
    private var mediaPlayer: MediaPlayer? = null

    private var sunnyBtn: ImageView? = null
    private var windyBtn: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_snow)
        sunImageView = findViewById(R.id.sunImageView)

        sunnyBtn = findViewById(R.id.sunnyBtn)
        windyBtn = findViewById(R.id.windyBtn)

        // bind the views
        val snowflake1ImageView: ImageView = findViewById(R.id.snowflake1ImageView)
        val snowflake2ImageView: ImageView = findViewById(R.id.snowflake2ImageView)
        val snowflake3ImageView: ImageView = findViewById(R.id.snowflake3ImageView)
        val snowflake4ImageView: ImageView = findViewById(R.id.snowflake4ImageView)
        val snowflake5ImageView: ImageView = findViewById(R.id.snowflake5ImageView)
        val snowflake6ImageView: ImageView = findViewById(R.id.snowflake6ImageView)
        val snowflake7ImageView: ImageView = findViewById(R.id.snowflake7ImageView)
        val snowflake8ImageView: ImageView = findViewById(R.id.snowflake8ImageView)
        val snowflake9ImageView: ImageView = findViewById(R.id.snowflake9ImageView)


        // Apply the animation to the snowflakes
        snowflake1ImageView.startAnimation(createSnowflakeAnimation())
        snowflake2ImageView.startAnimation(createSnowflakeAnimation())
        snowflake3ImageView.startAnimation(createSnowflakeAnimation())
        snowflake4ImageView.startAnimation(createSnowflakeAnimation())
        snowflake5ImageView.startAnimation(createSnowflakeAnimation())
        snowflake6ImageView.startAnimation(createSnowflakeAnimation())
        snowflake7ImageView.startAnimation(createSnowflakeAnimation())
        snowflake8ImageView.startAnimation(createSnowflakeAnimation())
        snowflake9ImageView.startAnimation(createSnowflakeAnimation())


        // Start jingle bell sounds
        mediaPlayer = MediaPlayer.create(this, R.raw.snow_sound)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.start()
        Log.d("SnowActivity", "snow sound started")

        sunnyBtn?.setOnClickListener {
            // Stop jingle bell sounds when the activity is stopped
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }

            // declare intent to start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        windyBtn?.setOnClickListener {
            // Stop jingle bell sounds when the activity is stopped
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }

            // declare intent to start WindyActivity
            val intent = Intent(this, WindyActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        // Stop jingle bell sounds when the activity is stopped
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
        Log.d("SnowActivity", "onStop")
    }

    private fun createSnowflakeAnimation(): TranslateAnimation {
        val random = Random()

        // Generate random start and end X values for the drift
        val fromX = random.nextFloat() * 2 - 1 // Random float between -1 and 1
        val toX = random.nextFloat() * 2 - 1 // Random float between -1 and 1

        // Create the animation from the random X values and drift from top to bottom of the screen
        val animation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_PARENT, fromX,
            TranslateAnimation.RELATIVE_TO_PARENT, toX,
            TranslateAnimation.RELATIVE_TO_PARENT, -1f,
            TranslateAnimation.RELATIVE_TO_PARENT, 1f
        )

        animation.duration =
            ((random.nextInt(3) + 6) * 1000).toLong() // Random duration between 6 and 9 seconds
        animation.repeatCount = TranslateAnimation.INFINITE // Repeat animation indefinitely
        animation.repeatMode = TranslateAnimation.RESTART // Start animation from beginning when it repeats

        return animation
    }
}
