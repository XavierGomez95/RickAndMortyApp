package com.rickandmortyapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmortyapi.data.model.EpisodeModel
import com.rickandmortyapi.data.repository.interfaces.EpisodeRepositoryInterface
import com.rickandmortyapi.data.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val episodeRepository: EpisodeRepositoryInterface
) : ViewModel() {
    private val _episodes = MutableStateFlow<Resource<List<EpisodeModel>>>(Resource.Init)
    val episodes: StateFlow<Resource<List<EpisodeModel>>> = _episodes

    fun fetchEpisodes() {
        viewModelScope.launch {
            _episodes.value = Resource.Loading
            _episodes.value = episodeRepository.retrieveAllEpisodes()
        }
    }

    private val _singleEpisode = MutableStateFlow<Resource<EpisodeModel>>(Resource.Init)
    val singleEpisode: StateFlow<Resource<EpisodeModel>> = _singleEpisode

    fun getEpisodeById(id: Int) {
        viewModelScope.launch {
            _singleEpisode.value = Resource.Loading
            _singleEpisode.value = episodeRepository.getEpisodeById(id)
        }
    }
}