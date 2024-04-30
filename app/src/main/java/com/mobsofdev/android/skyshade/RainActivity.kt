package com.mobsofdev.android.skyshade

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import java.util.Random


class RainActivity : AppCompatActivity() {

    private var mediaPlayer1: MediaPlayer? = null
    private var windyBtn: ImageView? = null
    private var sunnyBtn: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rain)

        windyBtn = findViewById(R.id.windyBtn)
        sunnyBtn = findViewById(R.id.sunnyBtn)

        // bind the views
        val lightningImageView1 = findViewById<ImageView>(R.id.lightning1ImageView)
        val lightningImageView2 = findViewById<ImageView>(R.id.lightning2ImageView)
        val lightningImageView3 = findViewById<ImageView>(R.id.lightning3ImageView)
        val lightningFlashView1 = findViewById<View>(R.id.lightningFlashView)
        val stormCloudImageView1 = findViewById<ConstraintLayout>(R.id.thundercloud1)
        val stormCloudImageView2 = findViewById<ConstraintLayout>(R.id.thundercloud2)
        val stormCloudImageView3 = findViewById<ConstraintLayout>(R.id.thundercloud3)


        // Create the media player for the lightning sound for the individual storm clouds
        val lightningSound1 = MediaPlayer.create(this, R.raw.lightning)
        val lightningSound2 = MediaPlayer.create(this, R.raw.lightning)
        val lightningSound3 = MediaPlayer.create(this, R.raw.lightning)

        // create the animation of the storm cloud group moving across the sky,
        // with a random offset to make the clouds move at different speeds
        stormCloudImageView1.startAnimation(createStormCloudAnimation(0.7))
        stormCloudImageView2.startAnimation(createStormCloudAnimation(1.0))
        stormCloudImageView3.startAnimation(createStormCloudAnimation(1.3))

        // create the animations of the lighting bolt flashing, calling the method with the
        // lightning image view, the animation, the lightning sound, the flash view, and the flash animation
        // for each of the three lightning image views on the screen

        // storm cloud 1
        startRandomLightningAnimation(
            lightningImageView1,
            createLightningAnimation(),
            lightningSound1,
            lightningFlashView1,
            createScreenFlashAnimation()
        )

        // storm cloud 2
        startRandomLightningAnimation(
            lightningImageView2,
            createLightningAnimation(),
            lightningSound2,
            lightningFlashView1,
            createScreenFlashAnimation()
        )

        // storm cloud 3
        startRandomLightningAnimation(
            lightningImageView3,
            createLightningAnimation(),
            lightningSound3,
            lightningFlashView1,
            createScreenFlashAnimation()
        )

        // Start the rain sounds
        mediaPlayer1 = MediaPlayer.create(this, R.raw.rain_sound)
        mediaPlayer1!!.isLooping = true
        mediaPlayer1!!.start()

        windyBtn?.setOnClickListener {
            // Stop rain sounds when the activity is stopped
            if (mediaPlayer1 != null) {
                mediaPlayer1!!.release()
                mediaPlayer1 = null
            }

            // declare intent to start RainActivity
            val intent = Intent(this, WindyActivity::class.java)
            startActivity(intent)
        }

        sunnyBtn?.setOnClickListener {
            // Stop rain sounds when the activity is stopped
            if (mediaPlayer1 != null) {
                mediaPlayer1!!.release()
                mediaPlayer1 = null
            }

            // declare intent to start MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStop() {
        super.onStop()
        // Stop rain sounds when the activity is stopped
        if (mediaPlayer1 != null) {
            mediaPlayer1!!.release()
            mediaPlayer1 = null
        }
    }

    // Create the animation of the storm cloud group moving across the sky, with a random offset
    // to make the clouds move at different speeds
    private fun createStormCloudAnimation(offSet: Double): TranslateAnimation {
        // Create the animation as a TranslateAnimation supplying the offset of where the cloud starts
        // and ends on the screen in x and y coordinates, these clouds are moving horizontally
        val animation = TranslateAnimation(
            TranslateAnimation.RELATIVE_TO_PARENT, -1.2f,
            TranslateAnimation.RELATIVE_TO_PARENT, 1.2f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0f,
            TranslateAnimation.RELATIVE_TO_PARENT, 0f
        )

        animation.duration = (10000 * offSet).toLong() // Duration in milliseconds
        animation.repeatCount = TranslateAnimation.INFINITE // Repeat the animation indefinitely
        animation.repeatMode = TranslateAnimation.RESTART // Restart the animation when it ends

        return animation
    }

    private fun createLightningAnimation(): AlphaAnimation {
        // Create the animation to make the lightning bolt appear and disappear
        val animation = AlphaAnimation(0f, 1f)

        animation.duration = 1000 // Duration in milliseconds
        animation.repeatCount = 1 // Repeat the animation once
        animation.repeatMode = Animation.REVERSE // Reverse the animation when it ends

        return animation
    }

    private fun startRandomLightningAnimation(
        cloudImageView: ImageView,
        animation: Animation,
        lightningSound: MediaPlayer,
        flashView: View,
        flashAnimation: Animation
    ) {
        val random = Random()

        // Start the animation after a random delay
        val delay = random.nextInt(5000).toLong() // Random delay between 0 and 5 seconds
        cloudImageView.postDelayed({

            // set the animation listener to start the lightning sound and screen flash animation
            // when the lightning bolt animation starts
            animation.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {
                    // Animation started
                    // Start the lightning sound
                    lightningSound.start()
                    // Start the screen flash animation
                    flashView.startAnimation(flashAnimation)
                }

                // set the animation listener to make the lightning bolt invisible when the
                // lightning bolt animation ends and restart the animation after a random delay
                override fun onAnimationEnd(animation: Animation) {
                    // Animation ended, make the view invisible
                    cloudImageView.visibility = View.INVISIBLE
                    // Restart the animation after a random delay
                    startRandomLightningAnimation(
                        cloudImageView,
                        animation,
                        lightningSound,
                        flashView,
                        flashAnimation
                    )
                }

                override fun onAnimationRepeat(animation: Animation) {
                    // Animation repeating
                }
            })
            cloudImageView.startAnimation(animation)
        }, delay)
    }

    private fun createScreenFlashAnimation(): AlphaAnimation {
        // Create the animation to make the screen flash white and then disappear quickly to simulate lightning
        // This animation is applied to a view that fills the screen
        val animation = AlphaAnimation(.7f, 0f) // Start fully visible, end completely transparent

        animation.duration = 100 // Duration in milliseconds
        animation.repeatCount = 1 // Repeat the animation once
        animation.repeatMode = Animation.REVERSE // Reverse the animation when it ends

        return animation
    }
}
