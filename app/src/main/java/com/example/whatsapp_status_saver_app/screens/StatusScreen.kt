package com.example.whatsapp_status_saver_app.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusScreen(navController: NavController) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Photos", "Videos")
    var selectedApp by remember { mutableStateOf("WhatsApp") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Row(
                        modifier = Modifier.padding(start = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.clickable { navController.popBackStack() }
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
                            text = "Status",
                            color = Color.Black,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateTopPadding()
            )
        ) {
            WhatsAppToggle(selectedOption = selectedApp) { selectedApp = it }
            TabRow(
                selectedTabIndex = selectedTabIndex,
                contentColor = Color(0XFF039840),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex])
                            .height(3.dp),
                        color = Color(0XFF039840)
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = title,
                                color = if (selectedTabIndex == index) Color(0XFF039840) else Color.Gray,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            when (selectedApp) {
                "WhatsApp" -> {
                    if (selectedTabIndex == 0) {
                        WhatsAppPhotoContent()
                    } else {
                        WhatsAppVideoContent()
                    }
                }
                "Business" -> {
                    if (selectedTabIndex == 0) {
                        BusinessPhotoContent()
                    } else {
                        BusinessVideoContent()
                    }
                }
            }
        }
    }
}

@Composable
fun WhatsAppPhotoContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("WhatsApp Photo Status Data", color = Color.Gray)
    }
}

@Composable
fun WhatsAppVideoContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("WhatsApp Video Status Data", color = Color.Gray)
    }
}

@Composable
fun BusinessPhotoContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Business Photo Status Data", color = Color.Gray)
    }
}

@Composable
fun BusinessVideoContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Business Video Status Data", color = Color.Gray)
    }
}

@Composable
fun WhatsAppToggle(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = listOf("WhatsApp", "Business")

    Row(
        modifier = Modifier.padding(10.dp)
    ) {
        options.forEach { option ->
            val isSelected = option == selectedOption
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isSelected) Color(0xFF00BFA5) else Color.Transparent
                    )
                    .clickable { onOptionSelected(option) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

