package com.krishworld.hiltexample.ui.viewpager

sealed class ViewPagerEvent {
    object PlayVideo : ViewPagerEvent()
    object PauseVideo : ViewPagerEvent()
    object PlayPauseVideo : ViewPagerEvent()
}