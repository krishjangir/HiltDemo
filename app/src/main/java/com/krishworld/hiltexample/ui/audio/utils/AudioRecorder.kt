package com.krishworld.hiltexample.ui.audio.utils

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.Date

class AudioRecorder(audioFileDirectory: String, val appContext: Context) {

    private var singleFile = true

    private var recorder: MediaRecorder? = null

    private val files = ArrayList<String>()

    private var fileDirectory: String? = null

    var audioFilePath: String? = null
        private set

    var isRecording: Boolean = false
        private set

    init {
        this.fileDirectory = audioFileDirectory

        if (this.fileDirectory?.endsWith("/") == false) {
            this.fileDirectory += "/"
        }

        newRecorder()
    }

    fun start(): Boolean {
        Log.d("start :","recorder : $recorder")
        prepareRecorder()

        try {
            recorder?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }

        if (recorder != null) {
            recorder?.start()
            isRecording = true
            return true
        }

        return false
    }

    fun pause(): Boolean {
        Log.d("pause :","recorder : $recorder  isRecording : $isRecording")
        return try {
            check(!(recorder == null || !isRecording)) { "[AudioRecorder] recorder is not recording!" }
            recorder?.stop()
            recorder?.release()
            recorder = null
            isRecording = false
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun resume(): Boolean {
        Log.d("resume :","isRecording : $isRecording")
        return try {
            check(!isRecording) { "[AudioRecorder] recorder is recording!" }
            singleFile = false
            newRecorder()
            return start()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun stop(): Boolean {
        Log.d("stop :","recorder : $recorder  isRecording : $isRecording")
        return try {
            if (!isRecording) {
                return merge()
            }

            if (recorder == null) {
                return false
            }

            recorder?.stop()
            recorder?.release()
            recorder = null
            isRecording = false

            return merge()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun clear() {
        if (recorder != null || isRecording) {
            recorder?.stop()
            recorder?.release()
            recorder = null
            isRecording = false
        }
        var i = 0
        val len = files.size
        while (i < len) {
            val file = File(this.files[i])
            file.delete()
            i++
        }
    }

    fun removePreviousRecording() {
        var i = 0
        val len = files.size
        while (i < len) {
            val file = File(this.files[0])
            file.delete()
            i++
        }
    }

    private fun merge(): Boolean {

        // If never paused, just return the file
        if (singleFile) {
            this.audioFilePath = this.files[0]
            return true
        }

        // Merge files
        val mergedFilePath = this.fileDirectory + Date().time + ".mp3"
        try {
            val fos = FileOutputStream(mergedFilePath)

            var i = 0
            val len = files.size
            while (i < len) {
                val file = File(this.files[i])
                val fis = FileInputStream(file)

                // Skip file header bytes,
                // amr file header's length is 6 bytes
                if (i > 0) {
                    for (j in 0..5) {
                        fis.read()
                    }
                }

                val buffer = ByteArray(512)

                fis.use { fis ->
                    var count: Int
                    while (fis.read(buffer) != -1) {
                        count = fis.read(buffer)
                        fos.write(buffer, 0, count)
                    }
                }

                fis.close()
                fos.flush()
                file.delete()
                i++
            }

            fos.flush()
            fos.close()

            this.audioFilePath = mergedFilePath
            return true
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return false
    }

    private fun newRecorder() {
        recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            MediaRecorder(appContext)
        } else {
            MediaRecorder()
        }
    }

    private fun prepareRecorder() {
        try {
            Log.d("AudioRecorder fileDirectory = ", "$fileDirectory")
            val directory = File(this.fileDirectory!!)
            Log.d("AudioRecorder directory = ", "$directory")
            require(!(!directory.exists() || !directory.isDirectory)) { "[AudioRecorder] audioFileDirectory is a not valid directory!" }

            val filePath = directory.absolutePath + "/" + Date().time + ".mp3"
            this.files.add(filePath)

            recorder!!.setOutputFile(filePath)
            recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            recorder!!.setAudioSamplingRate(48000)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}