package com.krishworld.hiltexample.ui.animation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.krishworld.hiltexample.R
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentAnimationBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AnimationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var appContext: Context

    private var _binding: FragmentAnimationBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val blinkAnimation: Animation =
            AnimationUtils.loadAnimation(appContext, R.anim.blink_animation)
        binding.textToBlink.animation = blinkAnimation

        val springAnimation = SpringAnimation(binding.animImage, SpringAnimation.TRANSLATION_X)
        val springForce = SpringForce()
        springForce.finalPosition = 900f // The final position to which the view will animate
        springForce.stiffness = SpringForce.STIFFNESS_MEDIUM // Adjust the stiffness of the spring
        springForce.dampingRatio = SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY // Adjust the damping ratio
        springAnimation.spring = springForce

        binding.animImage.setOnClickListener {
            springAnimation.start()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}