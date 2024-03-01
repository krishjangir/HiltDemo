package com.krishworld.hiltexample.ui.main.usecase

import com.krishworld.hiltexample.ui.main.MainUiModel
import com.krishworld.hiltexample.ui.main.Section


interface MainUseCase {
    fun getSectionsUiData(): Map<Section, List<MainUiModel>>
}