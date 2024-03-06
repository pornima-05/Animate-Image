package com.example.animationimage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private var selectedImage: ImageView? = null
    private var selectedBox: ImageView? = null
    private val savedImages = mutableListOf<ImageView>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onImageClick(view: View) {
        selectedImage = view as ImageView
        selectedBox = findViewById(R.id.saveBox)
        animateImageToBox(selectedImage)
    }

    fun animateImageToBox(image: ImageView?) {
        if (image != null && selectedBox != null) {
            val box = selectedBox!!

            val duplicateImage = ImageView(this)
            duplicateImage.setImageDrawable(image.drawable)

            duplicateImage.x = image.x
            duplicateImage.y = image.y
            duplicateImage.layoutParams = image.layoutParams

            val rootView = findViewById<View>(android.R.id.content)
            (rootView as? ViewGroup)?.addView(duplicateImage)

            val endX = box.x + (box.width - image.width) / 2
            val endY = box.y + (box.height - image.height) / 2

            val translationAnimation = TranslateAnimation(0f, endX - image.x, 0f, endY - image.y)

            val animationSet = AnimationSet(true)
            animationSet.addAnimation(translationAnimation)

            animationSet.duration = 1000
            animationSet.fillAfter = true

            animationSet.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}
                override fun onAnimationEnd(animation: Animation?) {
                    (rootView as? ViewGroup)?.removeView(duplicateImage)
                    savedImages.add(duplicateImage)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
            duplicateImage.startAnimation(animationSet)
            selectedImage = null
        }
    }

    fun onOrderButtonClick(view: View) {
        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val adapter = ImageSliderAdapter(savedImages)
        viewPager.adapter = adapter
    }
}