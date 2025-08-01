package com.example.whatsapp_status_saver_app

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.whatsapp_status_saver_app.screens.Navigation
import com.example.whatsapp_status_saver_app.screens.setLocale
import com.example.whatsapp_status_saver_app.ui.theme.WhatsApp_Status_Saver_AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val savedLanguageCode = prefs.getString("selectedLanguageCode", "en") ?: "en"
        setLocale(this, savedLanguageCode)

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatsApp_Status_Saver_AppTheme {
                val navController = rememberNavController()
                Navigation(navController)
            }
        }
    }
}
