package com.krishworld.hiltexample.ui.coroutine

import kotlinx.coroutines.delay

class FakeApiService {
    suspend fun fetchDataA(): List<Int> {
        delay(1000) // Simulate API delay
        return listOf(1, 2, 3)
    }

    suspend fun fetchDataB(): List<String> {
        delay(1500) // Simulate API delay
        return listOf("A", "B", "C")
    }
}