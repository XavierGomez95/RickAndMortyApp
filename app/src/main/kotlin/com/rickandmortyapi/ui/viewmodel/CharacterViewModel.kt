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

    fun getCharacterById(id: Int): Resource<CharacterModel> {
        val currentState = characterModelStateFlow.value

        // Si el estado aún es Init, lanza la carga y devuelve Loading
        if (currentState is Resource.Init) {
            fetchCharacters()
            return Resource.Loading
        }

        return when (currentState) {
            is Resource.Success -> {
                val character = currentState.data.find { it.id == id }
                if (character != null) {
                    Resource.Success(character)
                } else {
                    Resource.Failure
                }
            }
            is Resource.Failure -> Resource.Failure
            else -> Resource.Loading
        }
    }
}