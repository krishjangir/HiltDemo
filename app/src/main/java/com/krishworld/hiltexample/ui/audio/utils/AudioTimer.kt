package com.krishworld.hiltexample.ui.audio.utils

import android.os.Handler
import android.os.Looper

class AudioTimer(private val delayInterval: Long, private val callBack: CallBack) {
    private var handler: Handler? = null
    lateinit var runnable: Runnable
    private var stopped: Boolean = false

    interface CallBack {
        fun execute()
    }

    init {
        start()
    }

    fun stop() {
        stopped = true
        handler?.removeCallbacksAndMessages(null)
    }

    fun restart() {
        stop()
        start()
    }

    private fun start() {
        stopped = false
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            if (!stopped) {
                callBack.execute()
                runnable.let { handler?.postDelayed(it, delayInterval) }
            }
        }
        handler?.postDelayed(runnable, delayInterval)
    }
}