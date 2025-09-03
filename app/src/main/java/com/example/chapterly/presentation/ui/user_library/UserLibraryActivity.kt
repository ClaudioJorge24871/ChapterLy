package com.example.chapterly.presentation.ui.user_library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.chapterly.presentation.ui.user_library.ui.theme.ChapterlyTheme

class UserLibraryActivity : ComponentActivity() {
    private val viewModel: UserLibraryVIewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChapterlyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    UserLibraryScreen(viewModel = viewModel)
                }
            }
        }
    }
}