package com.example.chapterly

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chapterly.presentation.ui.theme.ChapterlyTheme
import com.example.chapterly.presentation.ui.user_library.UserLibraryActivity

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
                                onAddBookClick = {navController.navigate("addBook")}
                            )
                        }
                        composable("addBook") {
                            AddBookScreen(
                                onBookAdded = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }

    }
}