package com.rickandmortyapi.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rickandmortyapi.R
import com.rickandmortyapi.ui.theme.LocalSemanticColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchField(
    query: String,
    onQueryChange: (String) -> Unit,
    isActive: Boolean,
    onActiveChange: (Boolean) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier, // In case of not specified
    shape: Shape = RoundedCornerShape(45.dp), // In case of not specified
    borderWidth: Dp = 2.dp, // In case of not specified
) {
    val colors = LocalSemanticColors.current

    SearchBarDefaults.InputField(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = { onActiveChange(false) },
        expanded = isActive,
        onExpandedChange = onActiveChange,
        placeholder = {
            Text(
                text = placeholder,
                color = colors.placeholder.copy(alpha = 0.60f)
            )
        },
        leadingIcon = { Icon(Icons.Default.Search, null) },
        trailingIcon = {
            if (isActive) {
                IconButton(
                    onClick = {
                        onActiveChange(false)
                        onQueryChange("")
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = stringResource(R.string.search_bar_close_description)
                    )
                }
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(colors.customBackground)
            .border(
                width = borderWidth,
                color = colors.searchBarBorder,
                shape = shape
            ),
        colors = SearchBarDefaults.inputFieldColors(
            cursorColor = colors.cursorColor,
            focusedLeadingIconColor = colors.focusedLeadingIconColor,
            unfocusedLeadingIconColor = colors.unfocusedLeadingIconColor,
            focusedTrailingIconColor = colors.focusedTrailingIconColor,
            unfocusedTrailingIconColor = colors.unfocusedTrailingIconColor,
            focusedTextColor = colors.focusedTextColor,
        )
    )
}