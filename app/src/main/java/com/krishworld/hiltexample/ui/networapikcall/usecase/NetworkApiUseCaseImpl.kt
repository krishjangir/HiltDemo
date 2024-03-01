package com.krishworld.hiltexample.ui.networapikcall.usecase

import com.krishworld.hiltexample.data.model.Post
import com.krishworld.hiltexample.data.remote.Result
import com.krishworld.hiltexample.ui.networapikcall.repository.NetworkApiRepository
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.networapikcall.utility.PostSection
import javax.inject.Inject

class NetworkApiUseCaseImpl @Inject constructor(
    private val networkApiRepository: NetworkApiRepository
) : NetworkApiUseCase {
    override suspend fun getUserPosts(): Result<List<Post>> = networkApiRepository.getUserPosts()

    override suspend fun getUserPostsUiData(): Map<PostSection, List<MainPostUiModel>> {
        networkApiRepository.getUserPosts().apply {
            return getPostsUi(this)
        }
    }

    private fun getPostsUi(posts: Result<List<Post>>): Map<PostSection, List<MainPostUiModel>> {
        val sections = mutableMapOf<PostSection, List<MainPostUiModel>>()
        val postList = mutableListOf<MainPostUiModel>()

        /**
         * Prepare post data
         * */
        when (posts) {
            is Result.Success -> {
                posts.value.forEach { postList.add(MainPostUiModel.PostUiModel(it)) }
            }

            else -> {}
        }

        /**
         * Adding section data into section map
         * */
        sections[PostSection.ITEM_POST] = postList

        return sections
    }
}