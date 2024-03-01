package com.krishworld.hiltexample.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.base.Event
import com.krishworld.hiltexample.ui.main.LaunchEvent
import com.krishworld.hiltexample.ui.main.MainUiModel
import com.krishworld.hiltexample.ui.main.Section
import com.krishworld.hiltexample.ui.main.usecase.MainUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val mainUseCase: MainUseCase) : BaseViewModel() {
    // Main Ui Components
    private val _mainContentLiveData = MutableLiveData<List<MainUiModel>>()
    val mainContentLiveData: LiveData<List<MainUiModel>>
        get() = _mainContentLiveData

    // Navigation components
    private val _launchEventLiveData = MutableLiveData<Event<LaunchEvent>>()
    val launchEventLiveData: LiveData<Event<LaunchEvent>>
        get() = _launchEventLiveData

    // UI sections
    private var sections = mutableMapOf<Section, List<MainUiModel>>()

    init {
        fetchDataAndUpdateUI()
    }

    // fetching UI data here
    private fun fetchDataAndUpdateUI() {
        val sectionData = mainUseCase.getSectionsUiData()
        sections = sectionData as MutableMap<Section, List<MainUiModel>>
        _mainContentLiveData.value = sections.values.flatten()
    }

    // Article item click handle
    fun onArticleClick(articleData: MainUiModel.ArticlesUiModel) {
        if (!articleData.article.webUrl.isNullOrEmpty()) {
            _launchEventLiveData.value =
                Event(LaunchEvent.LaunchWebEvent(articleData.article.webUrl!!))
        }
    }
}