package com.krishworld.hiltexample.ui.audio

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.krishworld.hiltexample.R
import com.krishworld.hiltexample.base.ViewModelFactory
import com.krishworld.hiltexample.databinding.FragmentAudioRecorderBinding
import com.krishworld.hiltexample.ui.audio.utils.AudioRecorder
import com.krishworld.hiltexample.ui.audio.utils.AudioTimer
import com.krishworld.hiltexample.ui.audio.viewmodel.AudioRecorderViewModel
import com.krishworld.hiltexample.utils.Utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import javax.inject.Inject

@AndroidEntryPoint
class AudioRecorderFragment : Fragment(), MediaPlayer.OnCompletionListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var applicationContext: Context

    lateinit var audioRecorderViewModel: AudioRecorderViewModel

    private var _binding: FragmentAudioRecorderBinding? = null

    private val binding get() = _binding!!

    private var audioFilePath: String? = null
    var isStopRecordingToSave: Boolean = false
    var recorderSecondsElapsed: Int = 0
    var mRecordTimeInterval: Int = 0
    var isRecording: Boolean = false
    var timer: AudioTimer? = null
    private var mRecorder: AudioRecorder? = null
    var sdcardPath: String = ""
    var recordingDirectory: String = ""

    companion object {
        const val REQUEST_CODE_AUDIO_RECORD = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAudioRecorderBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        audioRecorderViewModel =
            ViewModelProvider(this, viewModelFactory)[AudioRecorderViewModel::class.java]
        binding.viewModel = audioRecorderViewModel

        recordingDirectory =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).absolutePath
        Log.d("AudioRecorder onViewCreated = ", "$recordingDirectory")

        //-----------Handle localization event here----------
        audioRecorderViewModel.localizationEventLiveData.observe(viewLifecycleOwner) {
            it.getContentIfNotHandled()?.let { param ->
                when (param) {
                    is AudioRecorderEvent.StartRecording -> startRecording()
                    is AudioRecorderEvent.StopRecording -> stopRecordings()
                    is AudioRecorderEvent.CancelRecording -> cancelRecording()
                }.exhaustive
            }
        }
    }

    private fun startRecording() {
        if (checkAudioRecordPermission()) {
            Log.d(
                "startRecording :",
                "isStopRecordingToSave : $isStopRecordingToSave, isRecording: $isRecording "
            )
            if (!isStopRecordingToSave) {
                if (isRecording) {
                    pauseRecording()
                } else {
                    resumeRecording()
                }
            } else { // save file
                Toast.makeText(applicationContext, audioFilePath, Toast.LENGTH_LONG).show()
            }
        } else {
            requestAudioRecordPermission()
        }
    }

    private fun stopRecordings() {
        Log.d("stopRecordings :", "mRecorder : $mRecorder")
        if (mRecorder == null) {
            stopTimer()
        }
        val isStopSuccess = mRecorder?.stop()
        Log.d("stopRecordings :", "isStopSuccess : $isStopSuccess")
        if (isStopSuccess != null && isStopSuccess) {
            audioFilePath = mRecorder?.audioFilePath
            Log.d("stopRecordings :", "audioFilePath : $audioFilePath")
            isRecording = false
            isStopRecordingToSave = true
            mRecordTimeInterval = 0
            recorderSecondsElapsed = 0
            mRecorder = null
        }
    }

    private fun cancelRecording() {
        if (isRecording) {
            stopRecording()
        }
        Toast.makeText(applicationContext, "cancelRecording Called", Toast.LENGTH_LONG).show()
    }

    private fun stopRecording() {
        Toast.makeText(applicationContext, "stopRecording Called", Toast.LENGTH_LONG).show()
        if (mRecorder == null) return
        mRecorder?.stop()
        audioFilePath = mRecorder?.audioFilePath
        recorderSecondsElapsed = 0
        mRecordTimeInterval = 0
        stopTimer()
        mRecorder = null
    }

    private fun pauseRecording() {
        isRecording = false
        if (mRecorder != null) {
            mRecorder?.pause()
        }
        binding.start.setImageResource(R.drawable.ic_record)
        stopTimer()
    }

    private fun resumeRecording() {
        isRecording = true
        if (mRecorder == null) {
            binding.minutes.text = "00"
            binding.seconds.text = "00"
            mRecorder = AudioRecorder(recordingDirectory, applicationContext)
            mRecorder?.removePreviousRecording()
            mRecorder?.start()
        } else {
            mRecorder?.resume()
        }
        binding.start.setImageResource(R.drawable.ic_pause)
        startTimer()
    }

    private fun startTimer() {
        stopTimer()
        timer = AudioTimer(1000, object : AudioTimer.CallBack {
            override fun execute() {
                val time = mRecordTimeInterval
                val min = time / 60
                val sec = time % 60
                val minStr = if (min < 10) "0$min" else "" + min
                val secStr = if (sec < 10) "0$sec" else "" + sec

                activity?.runOnUiThread {
                    if (isRecording) {
                        recorderSecondsElapsed++
                        binding.minutes.text = minStr
                        binding.seconds.text = secStr
                    }
                }
                mRecordTimeInterval++
            }
        })

    }

    private fun stopTimer() {
        if (timer != null) {
            timer?.stop()
            timer = null
        }
    }

    private fun checkAudioRecordPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        val result1 = ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.RECORD_AUDIO
        )
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    private fun requestAudioRecordPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO),
            REQUEST_CODE_AUDIO_RECORD
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_AUDIO_RECORD -> if (grantResults.isNotEmpty()) {
                val storagePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val recordPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (storagePermission && recordPermission) {
                    Toast.makeText(applicationContext, "Permission Granted", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    }

    override fun onDetach() {
        if (isRecording) stopRecording()

        super.onDetach()
    }

    override fun onDestroy() {
        if (isRecording) stopRecording()

        super.onDestroy()
    }

    override fun onCompletion(mp: MediaPlayer?) {
        mp?.release()
        binding.minutes.text = "00"
        binding.seconds.text = "00"
        binding.start.setImageResource(R.drawable.ic_record)
    }
}