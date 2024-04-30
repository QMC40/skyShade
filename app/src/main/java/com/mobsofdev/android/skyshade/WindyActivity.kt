package com.mobsofdev.android.skyshade

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class WindyActivity : AppCompatActivity() {
    private var sunImageView: ImageView? = null
    private var mediaPlayer: MediaPlayer? = null

    private var rainyBtn: ImageView? = null
    private var snowyBtn: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_windy)

        // Find the ImageView in layout
        sunImageView = findViewById(R.id.sunImageView)
        // Find the buttons in layout
        rainyBtn = findViewById(R.id.rainyBtn)
        snowyBtn = findViewById(R.id.snowyBtn)

        // Load and start animations for the sun
        val sunAnimation: Animation = AnimationUtils.loadAnimation(this, R.anim.sun_animation)
        sunImageView?.startAnimation(sunAnimation)

        // Find the ImageViews in your layout
        val leaf1ImageView = findViewById<ImageView>(R.id.leaf1ImageView)
        val leaf2ImageView = findViewById<ImageView>(R.id.leaf2ImageView)
        val leaf3ImageView = findViewById<ImageView>(R.id.leaf3ImageView)

        // Animate the individual leaves
        animateLeaf(leaf1ImageView, 5000, 1000, 0.6f)
        animateLeaf(leaf2ImageView, 6000, 1200, 0.7f)
        animateLeaf(leaf3ImageView, 7000, 1400, 0.8f)

        // Start wind sounds
        mediaPlayer = MediaPlayer.create(this, R.raw.wind_sound)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.start()

        rainyBtn?.setOnClickListener {
            // Stop wind sounds when the activity is stopped
            if (mediaPlayer != null) {
                mediaPlayer!!.release()
                mediaPlayer = null
            }

            // declare intent to start RainActivity
            val intent = Intent(this, RainActivity::class.java)
            startActivity(intent)
        }

        snowyBtn?.setOnClickListener {
            // Stop wind sounds when the activity is stopped
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
        // Stop wind sounds when the activity is stopped
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }


    private fun animateLeaf(
        leafImageView: ImageView,
        translateDuration: Long,
        rotateDuration: Long,
        translateDistance: Float
    ) {
        // Create a TranslateAnimation to move the leaf across the scree using the translateDistance
        // from the starting position in the layout to the supplied distance in x and y coordinates
        val translateAnimation = TranslateAnimation(
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, translateDistance,
            Animation.RELATIVE_TO_PARENT, 0f,
            Animation.RELATIVE_TO_PARENT, translateDistance
        ).apply {
            duration = translateDuration // duration in milliseconds
            repeatCount = Animation.INFINITE // repeat the animation indefinitely
            repeatMode = Animation.RESTART
        }

        // Create a RotateAnimation to rotate the leaf around its center as it moves across the screen
        val rotateAnimation = RotateAnimation(
            0f, 360f,
            // pivot point of X and Y coordinates
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = rotateDuration // duration in milliseconds
            repeatCount = Animation.INFINITE // repeat the animation indefinitely
        }

        // Create an AnimationSet to hold the animations and play them together
        val animationSet = AnimationSet(true).apply {
            addAnimation(translateAnimation)
            addAnimation(rotateAnimation)
        }

        // Start the animation
        leafImageView.startAnimation(animationSet)
    }
}
