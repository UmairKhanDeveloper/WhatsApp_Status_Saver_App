package com.example.whatsapp_status_saver_app.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectionScreen(navController: NavController) {
    val context = LocalContext.current
    val prefsManager = remember { SharedPrefsManager(context) }

    val languages = listOf(
        Language("English", "GB"),
        Language("اردو", "PK"),
        Language("Afrikaans", "ZA"),
        Language("Arabic", "SA"),
        Language("Bulgarian", "BG"),
        Language("Bangla", "BD"),
        Language("Bosnian", "BA")
    )

    var selectedLanguage by remember { mutableStateOf("English") }
    var selectedCountryCode by remember { mutableStateOf("GB") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Select Language",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = 80.dp)
                .padding(horizontal = 16.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(languages) { language ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedLanguage = language.name
                                selectedCountryCode = language.countryCode
                            }
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = countryCodeToFlag(language.countryCode),
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = language.name,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                        RadioButton(
                            selected = selectedLanguage == language.name,
                            onClick = {
                                selectedLanguage = language.name
                                selectedCountryCode = language.countryCode
                            },
                            colors = RadioButtonDefaults.colors(
                                selectedColor = Color(0xFF00C3A6)
                            )
                        )
                    }
                    Divider()
                }
            }

            Button(
                onClick = {
                    prefsManager.saveBoolean("isLanguageSelected", true)
                    prefsManager.saveString("selectedCountryCode", selectedCountryCode)

                    navController.navigate(Screens.HomeScreen.route + "/${selectedCountryCode}") {
                        popUpTo(Screens.LanguageSelectionScreen.route) { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00C3A6)
                ),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


data class Language(val name: String, val countryCode: String)

fun countryCodeToFlag(countryCode: String): String {
    return countryCode.uppercase()
        .map { char ->
            Character.toChars(char.code + 0x1F1A5).concatToString()
        }.joinToString("")
}


class SharedPrefsManager(context: Context) {
    private val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun saveBoolean(key: String, value: Boolean) {
        prefs.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return prefs.getBoolean(key, defaultValue)
    }

    fun saveString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return prefs.getString(key, defaultValue)
    }
}

