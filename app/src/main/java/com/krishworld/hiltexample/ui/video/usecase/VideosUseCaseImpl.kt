package com.krishworld.hiltexample.ui.video.usecase


import com.krishworld.hiltexample.data.local.repository.MainLocalRepository
import com.krishworld.hiltexample.data.local.VideosRawData
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.networapikcall.utility.PostSection
import com.krishworld.hiltexample.ui.video.utility.MainVideoUiModel
import com.krishworld.hiltexample.ui.video.utility.VideoSection
import javax.inject.Inject

class VideosUseCaseImpl @Inject constructor(
    private val mainLocalRepository: MainLocalRepository,
) : VideosUseCase {

    override fun getVideosUiData(): Map<VideoSection, List<MainVideoUiModel>> {
        return getVideosUi(mainLocalRepository.getVideosRawData())
    }

    private fun getVideosUi(videosRawData: VideosRawData): Map<VideoSection, List<MainVideoUiModel>> {
        val sections = mutableMapOf<VideoSection, List<MainVideoUiModel>>()
        val videos = mutableListOf<MainVideoUiModel>()

        /**
         * Prepare videos data
         * */
        videosRawData.videos.forEach { videos.add(MainVideoUiModel.VideosUiModel(it)) }

        /**
         * Adding section data into section map
         * */
        sections[VideoSection.ITEM_VIDEOS] = videos
        return sections
    }

}