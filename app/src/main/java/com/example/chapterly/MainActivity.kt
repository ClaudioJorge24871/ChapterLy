package com.example.chapterly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chapterly.presentation.ui.add_book.SaveBookScreen
import com.example.chapterly.presentation.ui.add_book.SaveBookViewModel
import com.example.chapterly.presentation.ui.theme.ChapterlyTheme
import com.example.chapterly.presentation.ui.user_library.UserLibraryRoute
import com.example.chapterly.presentation.ui.user_library.UserLibraryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChapterlyTheme {
                val navController = rememberNavController()

                Scaffold (
                    modifier = Modifier.fillMaxSize()
                ){ innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "library",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable("library") {
                            UserLibraryRoute(
                                onAddBookClick = {navController.navigate("saveBook")}
                            )
                        }
                        composable("saveBook") {
                            val saveBookViewModel: SaveBookViewModel = hiltViewModel()
                            val userLibraryViewModel: UserLibraryViewModel = hiltViewModel()

                            SaveBookScreen(
                                viewModel = saveBookViewModel,
                                onBookSaved = {
                                    userLibraryViewModel.loadBooks() // explicit refresh
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }

    }
}