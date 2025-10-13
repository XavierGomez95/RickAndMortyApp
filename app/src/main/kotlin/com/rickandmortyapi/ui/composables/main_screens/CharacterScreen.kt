package com.rickandmortyapi.ui.composables.main_screens

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rickandmortyapi.R
import com.rickandmortyapi.data.utils.Resource
import com.rickandmortyapi.ui.composables.character.CharacterDetailNav
import com.rickandmortyapi.ui.composables.FailureMessage
import com.rickandmortyapi.ui.composables.LoadingSpinner
import com.rickandmortyapi.ui.composables.generic_list_container.SearchListContainer
import com.rickandmortyapi.ui.viewmodel.CharacterViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CharacterScreen (
    navController: NavController,
    modifier: Modifier,
    characterViewModel: CharacterViewModel = hiltViewModel()
) {
    val charactersResource = characterViewModel.characterModelStateFlow.collectAsState().value

    var searchBar by remember { mutableStateOf("") }
    var isSearchBarActive by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (charactersResource is Resource.Init) {
            characterViewModel.fetchCharacters()
        }
    }

    when (charactersResource) {
        is Resource.Init -> Unit
        is Resource.Failure -> FailureMessage(messageError = stringResource(R.string.characters_failure))
        is Resource.Loading -> LoadingSpinner()
        is Resource.Success -> {
            val allCharacters = charactersResource.data
            val filteredCharacters = allCharacters.filter { currentCharacter ->
                (searchBar.isBlank() || currentCharacter.name.startsWith(searchBar, ignoreCase = true))
            }

            SearchListContainer(
                modifier = modifier,
                query = searchBar,
                onQueryChange = { searchBar = it},
                isActive = isSearchBarActive,
                onActiveChange = { isSearchBarActive = it},
                list = filteredCharacters,
                onItemClick = { selectedItem ->
                    navController.navigate(CharacterDetailNav(selectedItem.id))
                }
            )
        }
    }
}