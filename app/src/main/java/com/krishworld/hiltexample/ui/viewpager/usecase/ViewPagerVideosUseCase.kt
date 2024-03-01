package com.krishworld.hiltexample.ui.viewpager.usecase

import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerUiModel
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerVideoSection


interface ViewPagerVideosUseCase {
    fun getVideosUiData(): Map<ViewPagerVideoSection, List<ViewPagerUiModel>>
}