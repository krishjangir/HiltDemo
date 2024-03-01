package com.krishworld.hiltexample.ui.coroutine

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flattenConcat
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class CoroutinesViewModel @Inject constructor() : BaseViewModel() {

    private val _imgBitmapLiveData = MutableLiveData<Bitmap>()
    val imgBitmapLiveData: LiveData<Bitmap>
        get() = _imgBitmapLiveData

    private val apiService = FakeApiService()

    init {
        fetchDataCoroutines()
    }

    private fun fetchDataCoroutines() {
        viewModelScope.launch(Dispatchers.IO) {
            // Start multiple API calls concurrently
            /*val response1 = async { networkApiUseCase.getUserPostsUiData() }
              val response2 = async { networkApiUseCase.getUserPostsUiData() }
              val response3 = async { networkApiUseCase.getUserPostsUiData() }
            */

            val mergedResponse = listOf(
                async { apiService.fetchDataA() },
                async { apiService.fetchDataB() }
            )
            println("API Request: $mergedResponse")
            val mergedResponses = mergedResponse.awaitAll()
            println("API Response mergedResponse: $mergedResponses")

            // Make API calls sequentially
            val response1 = async { apiService.fetchDataA() }.await()
            val response2 = async { apiService.fetchDataB() }.await()


            // Wait for all API calls to complete and retrieve the results
            /*val result1 = response1.await()
            val result2 = response2.await()
            val result3 = response3.await()*/

            // Process the results
            /*println("API Response 1: $result1")
            println("API Response 2: $result2")
            println("API Response 3: $result3")*/

            println("API Response 1: $response1")
            println("API Response 2: $response2")


            // zip results
            val flowA = flowOf(1, 2, 3)
            val flowB = flowOf("A", "B", "C")

            val zippedFlow: Flow<Pair<Int, String>> = flowA.zip(flowB) { a, b -> a to b }

            zippedFlow.collect { (num, str) ->
                println("$num -> $str")
            }


            val flowA1: Flow<List<Int>> = flow { emit(apiService.fetchDataA()) }
            val flowB1: Flow<List<String>> = flow { emit(apiService.fetchDataB()) }

            val zippedFlow1: Flow<Pair<List<Int>, List<String>>> = flowA1.zip(flowB1) { a, b -> a to b }

            zippedFlow1.collect { (listA, listB) ->
                println("Data from Flow A1: $listA")
                println("Data from Flow B1: $listB")
            }

            // concatenated results
            val flowC = flowOf(1, 2, 3)
            val flowD = flowOf(4, 5, 6)

            val concatenatedFlow: Flow<Int> = flowOf(flowC, flowD).flattenConcat()

            concatenatedFlow.collect { num ->
                println(num)
            }


            val flowC1: Flow<List<Int>> = flow { emit(apiService.fetchDataA()) }
            val flowD1: Flow<List<String>> = flow { emit(apiService.fetchDataB()) }

            val concatenatedFlow1: Flow<List<Any>> = flowOf(flowC1, flowD1).flattenConcat()

            concatenatedFlow1.collect { list ->
                println("Concatenated Data: $list")
            }


            // merged results
            val flowE = flowOf(1, 3, 5).onEach { kotlinx.coroutines.delay(100) }
            val flowF = flowOf(2, 4, 6).onEach { kotlinx.coroutines.delay(150) }

            val mergedFlow: Flow<Int> = flowOf(flowE, flowF).flattenMerge()

            mergedFlow.collect { num ->
                println(num)
            }

            val flowE1: Flow<List<Int>> = flow { emit(apiService.fetchDataA()) }
            val flowF1: Flow<List<String>> = flow { emit(apiService.fetchDataB()) }

            val mergedFlow1: Flow<Any> = flowOf(flowE1, flowF1).flattenMerge()

            mergedFlow1.collect { data ->
                println("Merged Data: $data")
            }

        }
    }


    fun downloadImage(imageUrl: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val imgBitmap = downloadImg(imageUrl)
            _imgBitmapLiveData.postValue(imgBitmap)
        }
    }


    // Function to download the image from the provided URL
    private fun downloadImg(imageUrl: String?): Bitmap {
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
}