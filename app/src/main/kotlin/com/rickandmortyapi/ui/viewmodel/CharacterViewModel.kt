package com.rickandmortyapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rickandmortyapi.data.model.CharacterModel
import com.rickandmortyapi.data.repository.interfaces.CharacterRepositoryInterface
import com.rickandmortyapi.data.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val characterRepositoryInterface: CharacterRepositoryInterface
) : ViewModel() {
    private val _characterModelMutableStateFlow = MutableStateFlow<Resource<List<CharacterModel>>>(Resource.Init)
    val characterModelStateFlow: StateFlow<Resource<List<CharacterModel>>> = _characterModelMutableStateFlow

    fun fetchCharacters() {
        viewModelScope.launch {
            _characterModelMutableStateFlow.value = Resource.Loading
            _characterModelMutableStateFlow.value = characterRepositoryInterface.retrieveAllCharacters()
        }
    }

    private val _singleCharacterStateFlow = MutableStateFlow<Resource<CharacterModel>>(Resource.Init)
    val singleCharacterStateFlow: StateFlow<Resource<CharacterModel>> = _singleCharacterStateFlow

    fun getCharacterById(id: Int) {
        viewModelScope.launch {
            _singleCharacterStateFlow.value = Resource.Loading
            _singleCharacterStateFlow.value = characterRepositoryInterface.getCharacterById(id)
        }
    }
}