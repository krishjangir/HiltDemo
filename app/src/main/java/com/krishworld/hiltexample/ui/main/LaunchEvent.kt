package com.krishworld.hiltexample.ui.main

sealed class LaunchEvent {
    object LaunchAppointments : LaunchEvent()
    data class LaunchWebEvent(val webUrl: String) : LaunchEvent()
}
