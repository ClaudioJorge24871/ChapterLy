package com.example.chapterly.presentation.ui.common

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionContext
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.DialogProperties
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import dagger.hilt.internal.aggregatedroot.codegen._com_example_chapterly_ChapterlyApplication
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Composable file to use as Date fields in screens
 */
@Composable
fun DateField(
    label: String,
    date: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    var pickedDate by remember {
        mutableStateOf(date ?: LocalDate.now())
    }
    val context = LocalContext.current
    val dateDialogState = rememberMaterialDialogState()


    Button(
        onClick = {
            dateDialogState.show()
        }
    ){
        Text(text = date?.toString() ?: label)
    }
    MaterialDialog(
        dialogState = dateDialogState,
        properties = DialogProperties(),
        buttons = {
            positiveButton(text = "Ok") {
                onDateSelected(pickedDate)
                Toast.makeText(
                    context,
                    "$label saved!",
                    Toast.LENGTH_SHORT
                ).show()
            }
            negativeButton(text = "Cancel")
        }
    ) {
        datepicker(
            initialDate = pickedDate,
        ) { picked ->
            pickedDate = picked
        }
    }
}