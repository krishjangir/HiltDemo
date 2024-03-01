package com.krishworld.hiltexample.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.krishworld.hiltexample.utils.Extensions.gone

object Extensions {

    fun View.visible() {
        this.visibility = View.VISIBLE
    }

    fun View.gone() {
        this.visibility = View.GONE
    }

    fun View.delayedGone() {
        this.visible()
        this.postDelayed({
            this.gone()
        }, 2000)
    }

    private fun View.animateMarginBottom(size: Float, duration: Long) {
        val dpToPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            size,
            resources.displayMetrics
        )


        val params =
            this.layoutParams as ConstraintLayout.LayoutParams
        val animator = ValueAnimator.ofInt(params.bottomMargin, dpToPx.toInt())
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            params.setMargins(
                params.leftMargin,
                params.topMargin,
                params.rightMargin,
                value
            )
            this.layoutParams = params
        }
        animator.duration = duration
        animator.start()
    }

    fun View.animateTranslationY(animateFrom: Float, animateTo: Float, duration: Long) {
        val animator =
            ObjectAnimator.ofFloat(
                this, "translationY", TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    animateTo,
                    resources.displayMetrics
                ), TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    animateFrom,
                    resources.displayMetrics
                )
            )
        animator.duration = duration
        if (animateTo == 0f) {
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    this@animateTranslationY.gone()
                }
            })
        }
        animator.start()

    }

    fun BottomNavigationView.showWithAnimation(fragmentContainerView: View) {
        if (this.visibility == View.VISIBLE) return
        this.visible()
        this.animateTranslationY(0f, 60f, 700)
        fragmentContainerView.animateMarginBottom(60f, 700)
    }

    fun BottomNavigationView.hideWithAnimation(fragmentContainerView: View) {
        if (this.visibility == View.GONE) return
        this.animateTranslationY(60f, 0f, 700)
        fragmentContainerView.animateMarginBottom(0f, 700)
    }

    fun BottomNavigationView.hideWithoutAnimation(fragmentContainerView: View) {
        if (this.visibility == View.GONE) return
        this.gone()

        val params =
            fragmentContainerView.layoutParams as ConstraintLayout.LayoutParams
        params.setMargins(
            params.leftMargin,
            params.topMargin,
            params.rightMargin,
            0
        )
        fragmentContainerView.layoutParams = params

    }
}