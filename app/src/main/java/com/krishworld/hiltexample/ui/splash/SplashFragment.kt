package com.krishworld.hiltexample.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.krishworld.hiltexample.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    var progressBarJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val action = SplashFragmentDirections.actionSplashFragmentToLocalizationFragment()
        progressBarJob = coroutineScope.launch {
            delay(3000)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        progressBarJob?.cancel()
    }
}