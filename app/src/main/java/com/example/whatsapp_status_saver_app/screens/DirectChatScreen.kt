package com.example.whatsapp_status_saver_app.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DirectChatScreen(navController: NavController) {
    var number by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedCode by remember { mutableStateOf("92") }

    Scaffold(topBar = {
        TopAppBar(
            title = {},
            navigationIcon = {
                Row(
                    modifier = Modifier.padding(start = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .clickable { navController.popBackStack() }
                            .clip(RoundedCornerShape(10.dp))
                            .size(32.dp)
                            .background(Color(0XFF039840).copy(alpha = 0.100f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back",
                            tint = Color(0XFF039840)
                        )
                    }
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Direct Chat",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        )
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding(),
                    start = 16.dp,
                    end = 16.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CountryDropdown(onCountrySelected = { code ->
                selectedCode = code.toString()
            })

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = "+$selectedCode $number",
                onValueChange = { input ->
                    number = input.removePrefix("+$selectedCode ").filter { it.isDigit() }
                },
                placeholder = { Text("Enter Your Number", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(4.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFDEDEDE),
                    unfocusedIndicatorColor = Color(0xFFDEDEDE),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                placeholder = { Text("Write Your Message...", color = Color.Gray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(4.dp)),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFDEDEDE),
                    unfocusedIndicatorColor = Color(0xFFDEDEDE),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .clip(RoundedCornerShape(25.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF039840))
            ) {
                Text("Send", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun CountryDropdown(onCountrySelected: (Int) -> Unit) {
    val countries = listOf(
        Triple("🇵🇰", "Pakistan (PK)", 92),
        Triple("🇮🇳", "India (IN)", 91),
        Triple("🇺🇸", "United States (US)", 1),
        Triple("🇬🇧", "United Kingdom (GB)", 44),
        Triple("🇨🇦", "Canada (CA)", 1),
        Triple("🇦🇺", "Australia (AU)", 61),
        Triple("🇩🇪", "Germany (DE)", 49),
        Triple("🇫🇷", "France (FR)", 33),
        Triple("🇸🇦", "Saudi Arabia (SA)", 966),
        Triple("🇦🇪", "UAE (AE)", 971)
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedCountry by remember { mutableStateOf(countries[0]) }

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0XFF039840).copy(alpha = 0.100f))
                .clickable { expanded = true }
                .padding(horizontal = 12.dp, vertical = 12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(selectedCountry.first, fontSize = 18.sp, color = Color(0XFF039840))
                    Spacer(Modifier.width(8.dp))
                    Text("${selectedCountry.second} ${selectedCountry.third}", color = Color(0XFF039840))
                }
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color(0XFF039840))
            }
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            countries.forEachIndexed { index, country ->
                DropdownMenuItem(
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(country.first, fontSize = 18.sp)
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(country.second, fontWeight = FontWeight.Medium, fontSize = 14.sp)
                                Text("+${country.third}", fontSize = 12.sp, color = Color.Gray)
                            }
                        }
                    },
                    onClick = {
                        selectedCountry = country
                        onCountrySelected(country.third)
                        expanded = false
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                if (index != countries.lastIndex) {
                    Divider(thickness = 0.5.dp, color = Color(0xFFE0E0E0))
                }
            }
        }
    }
}
