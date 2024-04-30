package com.mobsofdev.android.skyshade

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var sunImageView: ImageView? = null
    private var cloud1ImageView: ImageView? = null
    private var cloud2ImageView: ImageView? = null
    private var cloud3ImageView: ImageView? = null
    private var mediaPlayer: MediaPlayer? = null

    private var rainyBtn: ImageView? = null
    private var snowyBtn: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find views by id
        sunImageView = findViewById(R.id.sunImageView)
        cloud1ImageView = findViewById(R.id.cloud1ImageView)
        cloud2ImageView = findViewById(R.id.cloud2ImageView)
        cloud3ImageView = findViewById(R.id.cloud3ImageView)
        rainyBtn = findViewById(R.id.rainyBtn)
        snowyBtn = findViewById(R.id.snowyBtn)

        // Load and start animations
        val sunAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.sun_animation)
        sunImageView?.startAnimation(sunAnimation)
        val cloudAnimation1: Animation = AnimationUtils.loadAnimation(this, R.anim.cloud_animation1)
        val cloudAnimation2: Animation = AnimationUtils.loadAnimation(this, R.anim.cloud_animation2)
        val cloudAnimation3: Animation = AnimationUtils.loadAnimation(this, R.anim.cloud_animation3)
        cloud1ImageView?.startAnimation(cloudAnimation1)
        cloud2ImageView?.startAnimation(cloudAnimation2)
        cloud3ImageView?.startAnimation(cloudAnimation3)

        // Start birds singing
        mediaPlayer = MediaPlayer.create(this, R.raw.birds_sound)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.start()

        rainyBtn?.setOnClickListener {
            // Stop birds singing when the activity is stopped
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }

            // declare intent to start RainActivity
            val intent = Intent(this, RainActivity::class.java)
            startActivity(intent)
        }

        snowyBtn?.setOnClickListener {
            // Stop birds singing when the activity is stopped
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }

            // declare intent to start SnowActivity
            val intent = Intent(this, SnowActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        // Stop birds singing when the activity is stopped
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }
}
