package com.example.vknews.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.vknews.presentation.theme.Test
import com.example.vknews.presentation.theme.VKNewsTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VKNewsTheme {
                Test()
//                MainScreen()
            }
        }
    }
}