package com.krishworld.hiltexample.ui.main

import com.krishworld.hiltexample.data.local.Articles

sealed class MainUiModel(
    open val viewType: MainRvViewType,
    open val viewId: String = ""
) {
    data class ArticlesUiModel(var article: Articles) : MainUiModel(
        viewType = MainRvViewType.ITEM_ARTICLE,
        viewId = MainRvViewType.ITEM_ARTICLE.name
    )

    data class SectionHeaderUiModel(var sectionHeader: String) : MainUiModel(
        viewType = MainRvViewType.ITEM_SECTION_HEADER,
        viewId = MainRvViewType.ITEM_SECTION_HEADER.name
    )

    data class SectionDividerUiModel(
        override val viewType: MainRvViewType = MainRvViewType.ITEM_SECTION_DIVIDER
    ) : MainUiModel(
        viewType = MainRvViewType.ITEM_SECTION_DIVIDER,
        viewId = MainRvViewType.ITEM_SECTION_DIVIDER.name
    )
}