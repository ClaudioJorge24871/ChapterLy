package com.example.chapterly

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chapterly.data.local.entities.BookEntryEntity
import com.example.chapterly.domain.model.Book
import com.example.chapterly.presentation.mapper.toUIData
import com.example.chapterly.presentation.ui.add_book.SaveBookScreen
import com.example.chapterly.presentation.ui.add_book.SaveBookViewModel
import com.example.chapterly.presentation.ui.theme.ChapterlyTheme
import com.example.chapterly.presentation.ui.updated_book.UpdateBookScreen
import com.example.chapterly.presentation.ui.user_library.UserLibraryRoute
import com.example.chapterly.presentation.ui.user_library.UserLibraryViewModel
import com.example.chapterly.resources.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChapterlyTheme {
                val navController = rememberNavController()
                val userLibraryViewModel: UserLibraryViewModel = hiltViewModel()
                val saveBookViewModel: SaveBookViewModel = hiltViewModel()


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
                                viewModel = userLibraryViewModel,
                                onAddBookClick = {navController.navigate("saveBook")},
                                onSelectedBook = { isbn ->
                                    // encode in case isbn contains special chars
                                    val encoded = Uri.encode(isbn)
                                    navController.navigate("updateBook/$encoded")
                                }
                            )
                        }
                        composable("saveBook") {
                            SaveBookScreen(
                                viewModel = saveBookViewModel,
                                onBookSaved = {
                                    userLibraryViewModel.loadBooks() // explicit refresh
                                    navController.popBackStack()
                                },
                                onGoBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable( "updateBook/{isbn}" ){ backStackEntry ->
                            val isbn = backStackEntry.arguments?.getString("isbn") ?: return@composable
                            LaunchedEffect(isbn) {
                                userLibraryViewModel.getUserBookByISBN(isbn)
                            }

                            val selectedResult by userLibraryViewModel.selectedBook.collectAsState()
                            when (val r = selectedResult){
                                null -> {}
                                is Result.Loading -> {
                                    // show loading UI
                                    androidx.compose.material3.CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                                }
                                is Result.Success -> {
                                    val domainEntry = r.data
                                    val initialBookUI = domainEntry.toUIData()

                                    UpdateBookScreen(
                                        viewModel = saveBookViewModel,
                                        selectedBook = initialBookUI,
                                        onBookUpdated = {
                                            userLibraryViewModel.loadBooks()
                                            userLibraryViewModel.clearSelectedBook()
                                            navController.popBackStack()
                                        },
                                        onGoBackClicked = {
                                            userLibraryViewModel.clearSelectedBook()
                                            navController.popBackStack()
                                        }
                                    )
                                }
                                is Result.Error -> {
                                    Text("Error loading book: ${r.error}")
                                }
                            }

                        }
                    }
                }
            }
        }

    }
}