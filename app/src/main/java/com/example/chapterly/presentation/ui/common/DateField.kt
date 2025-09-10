package com.example.chapterly.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DateField(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier
) {
    val dateDialogState = rememberMaterialDialogState()
    var pickedDate by remember { mutableStateOf(date ?: LocalDate.now()) }
    val formatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }
    val formattedDate = date?.format(formatter) ?: ""

    OutlinedTextField(
        value = formattedDate,
        onValueChange = {},
        readOnly = true,
        label = { Text(label) },
        trailingIcon = {
            IconButton(onClick = { dateDialogState.show() }) {
                Icon(Icons.Default.DateRange, contentDescription = "Select date")
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .clickable{dateDialogState.show()},
        enabled = false,
        colors = OutlinedTextFieldDefaults.colors(
            disabledContainerColor = OutlinedTextFieldDefaults.colors().unfocusedContainerColor,
            disabledTextColor = MaterialTheme.colorScheme.onSurface,
            disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledBorderColor = MaterialTheme.colorScheme.outline
        ),
    )

    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton("Ok") { onDateSelected(pickedDate) }
            negativeButton("Cancel")
        }
    ) {
        datepicker(initialDate = pickedDate) { picked -> pickedDate = picked }
    }
}
