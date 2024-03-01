package com.krishworld.hiltexample.ui.viewpager.usecase

import com.krishworld.hiltexample.data.local.VideosRawData
import com.krishworld.hiltexample.data.local.repository.MainLocalRepository
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerUiModel
import com.krishworld.hiltexample.ui.viewpager.utility.ViewPagerVideoSection
import javax.inject.Inject

class ViewPagerVideosUseCaseImpl @Inject constructor(
    private val mainLocalRepository: MainLocalRepository,
) : ViewPagerVideosUseCase {
    override fun getVideosUiData(): Map<ViewPagerVideoSection, List<ViewPagerUiModel>> {
        return getVideosUi(mainLocalRepository.getVideosRawData())
    }

    private fun getVideosUi(videosRawData: VideosRawData): Map<ViewPagerVideoSection, List<ViewPagerUiModel>> {
        val sections = mutableMapOf<ViewPagerVideoSection, List<ViewPagerUiModel>>()
        val videos = mutableListOf<ViewPagerUiModel>()

        /**
         * Prepare videos data
         * */
        videosRawData.videos.forEach { videos.add(ViewPagerUiModel.VideosUiModel(it)) }

        /**
         * Adding section data into section map
         * */
        sections[ViewPagerVideoSection.ITEM_VIDEOS] = videos
        return sections
    }
}