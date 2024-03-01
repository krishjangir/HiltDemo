package com.krishworld.hiltexample.ui.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class ToastWorker(
    val context: Context,
    params: WorkerParameters
) : Worker(context, params) {
    override fun doWork(): Result {
        val outputData = Data.Builder()
        outputData.putString("toastMsg", "Toast from Worker")
        return Result.success(outputData.build())
    }
}
