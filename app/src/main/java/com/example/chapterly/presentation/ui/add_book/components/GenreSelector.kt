package com.example.chapterly.presentation.ui.add_book.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.chapterly.domain.model.Genre

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenreSelector (
    selectedGenres: Set<Genre>,
    onSelectionChanged: (Set<Genre>) -> Unit
){
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        Text(text = "Genres", style = MaterialTheme.typography.titleMedium)

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Genre.entries.forEach { genre ->
                val selected = selectedGenres.contains(genre)
                FilterChip(
                    selected = selected,
                    onClick = {
                        val newSelection = selectedGenres.toMutableSet()
                        if (selected) newSelection.remove(genre) else newSelection.add(genre)
                        onSelectionChanged(newSelection)
                    },
                    label = { Text(text = genre.displayName)},
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
            }
        }
    }
}