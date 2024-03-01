package com.krishworld.hiltexample.ui.workmanager

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.Paint
import android.provider.MediaStore
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DownloadImageWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        val imageUrl = inputData.getString("imageUrl")
        val outputData = Data.Builder()

        try {
            // Download the image from the provided URL
            val bitmap = downloadImage(imageUrl)

            // Apply filter to the downloaded image
            val filteredBitmap = applyFilter(bitmap)

            // Save the filtered image to storage
            val imagePath = saveImageToStorage(filteredBitmap)

            // Pass the image path as output data
            outputData.putString("imagePath", imagePath)

            return Result.success(outputData.build())
        } catch (e: Exception) {
            // Handle any errors and return failure
            return Result.failure()
        }
    }

    // Function to download the image from the provided URL
    private fun downloadImage(imageUrl: String?): Bitmap {
        // Implement the image download logic here
        val url = URL(imageUrl)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 10000 // Set connection timeout
        connection.readTimeout = 15000 // Set read timeout
        connection.requestMethod = "GET"

        val responseCode = connection.responseCode
        if (responseCode == HttpURLConnection.HTTP_OK) {
            val inputStream = connection.inputStream
            return BitmapFactory.decodeStream(inputStream)
        }

        throw IOException("Failed to download image. Response code: $responseCode")

    }

    // Function to apply the desired filter to the image
    private fun applyFilter(bitmap: Bitmap): Bitmap {
        // Implement the image filtering logic here
        val filteredBitmap =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(filteredBitmap)
        val paint = Paint().apply {
            val colorMatrix = ColorMatrix().apply {
                setSaturation(0f) // Set saturation to 0 for grayscale effect
            }
        }
        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return filteredBitmap
    }

    // Function to save the filtered image to storage and return the image path
    private fun saveImageToStorage(bitmap: Bitmap): String {
        // Implement the image saving logic here and return the image path
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "IMG_$timeStamp.jpg"

        val resolver = applicationContext.contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, imageFileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }

        val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            ?: throw IOException("Failed to create image file")

        val outputStream = resolver.openOutputStream(imageUri)
            ?: throw IOException("Failed to open output stream")

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.close()

        return imageUri.toString()
    }
}
