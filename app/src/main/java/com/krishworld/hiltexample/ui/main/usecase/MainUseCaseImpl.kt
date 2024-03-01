package com.krishworld.hiltexample.ui.main.usecase


import com.krishworld.hiltexample.data.local.repository.MainLocalRepository
import com.krishworld.hiltexample.data.local.ArticlesRawData
import com.krishworld.hiltexample.ui.main.MainUiModel
import com.krishworld.hiltexample.ui.main.Section
import javax.inject.Inject

class MainUseCaseImpl @Inject constructor(
    private val mainLocalRepository: MainLocalRepository,
) : MainUseCase {

    override fun getSectionsUiData(): Map<Section, List<MainUiModel>> {
        return getSectionsUi(mainLocalRepository.getArticlesRawData())
    }

    private fun getSectionsUi(sectionsData: ArticlesRawData): Map<Section, List<MainUiModel>> {
        val sections = mutableMapOf<Section, List<MainUiModel>>()
        val articles = mutableListOf<MainUiModel>()

        /**
         * Prepare Article data
         * */
        articles.add(MainUiModel.SectionHeaderUiModel("Articles"))
        sectionsData.articles.forEach { articles.add(MainUiModel.ArticlesUiModel(it)) }
        articles.add(MainUiModel.SectionDividerUiModel())

        /**
         * Adding section data into section map
         * */
        sections[Section.ITEM_ARTICLE] = articles
        return sections
    }

}