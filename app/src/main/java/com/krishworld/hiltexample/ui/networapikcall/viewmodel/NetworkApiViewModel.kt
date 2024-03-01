package com.krishworld.hiltexample.ui.networapikcall.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.krishworld.hiltexample.base.BaseViewModel
import com.krishworld.hiltexample.ui.networapikcall.usecase.NetworkApiUseCase
import com.krishworld.hiltexample.ui.networapikcall.utility.MainPostUiModel
import com.krishworld.hiltexample.ui.networapikcall.utility.PostSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NetworkApiViewModel @Inject constructor(
    private val networkApiUseCase: NetworkApiUseCase,
    private val coroutineDispatcher: CoroutineDispatcher
) :
    BaseViewModel() {

    // Main Ui Components
    private val _mainContentLiveData = MutableLiveData<List<MainPostUiModel>>()
    val mainContentLiveData: LiveData<List<MainPostUiModel>>
        get() = _mainContentLiveData

    // UI sections
    private var sections = mutableMapOf<PostSection, List<MainPostUiModel>>()

    init {
        fetchDataAndUpdateUI()
    }

    // fetching UI data here
    private fun fetchDataAndUpdateUI() {
        viewModelScope.launch(coroutineDispatcher) {
            networkApiUseCase.getUserPostsUiData().apply {
                sections = this as MutableMap<PostSection, List<MainPostUiModel>>
                _mainContentLiveData.postValue(sections.values.flatten())
            }
        }

    }
}