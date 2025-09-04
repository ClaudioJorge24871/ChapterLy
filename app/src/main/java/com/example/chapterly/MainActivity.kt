package com.example.chapterly

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chapterly.presentation.ui.user_library.UserLibraryActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Redirects to User library on app launch
        val intent = Intent(this, UserLibraryActivity::class.java)
        startActivity(intent)

        //Prevent going back to MainActivity
        finish()
    }
}