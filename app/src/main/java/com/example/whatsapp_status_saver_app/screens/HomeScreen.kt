package com.example.whatsapp_status_saver_app.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.whatsapp_status_saver_app.R


@Composable
fun HomeScreen(navController: NavController, countryCode: String?) {
    val context = LocalContext.current
    val prefsManager = remember { SharedPrefsManager(context) }

    val languageCode = remember {
        countryCode ?: prefsManager.getString("selectedCountryCode", "GB") ?: "GB"
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(129.dp)
                    .background(Color(0xFF00B09C))
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Status Up",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "All in one status saver",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        navController.navigate(Screens.LanguageSelectionScreen.route)
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.feather_icon),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = languageCode,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }

        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 100.dp)
                .width(340.dp)
                .height(150.dp),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SelectionWhats(
                    title = "WhatsApp",
                    icon = R.drawable.image_1
                ) { navController.navigate(Screens.StatusScreen.route) }

                SelectionWhats(
                    title = "WhatsApp Business",
                    icon = R.drawable.image_2
                ) { navController.navigate(Screens.StatusScreen.route) }

                SelectionWhats(title = "WhatsApp Web", icon = R.drawable.image_3) {}
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 270.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CardItem(
                title = "Direct Chat",
                icon = R.drawable.telegramlogo
            ) { navController.navigate(Screens.DirectChatScreen.route) }
            CardItem(title = "Saved Files", icon = R.drawable.folder) {}
        }

        Card(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 360.dp)
                .width(340.dp)
                .height(120.dp),
            shape = RoundedCornerShape(6.dp),
            elevation = CardDefaults.cardElevation(1.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Color(0xFFE0E0E0))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SettingItem(title = "Rate US", icon = R.drawable.star) {}
                SettingItem(title = "Share App", icon = R.drawable.share) {}
                SettingItem(title = "Privacy Policy", icon = R.drawable.shield) {}
            }
        }
    }
}


@Composable
fun SelectionWhats(
    title: String,
    icon: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(90.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF00B09C)),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(
                        color = Color.White.copy(alpha = 0.15f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = title,
                fontSize = 11.sp,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun CardItem(title: String, icon: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(165.dp)
            .height(70.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFF00B09C),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = title,
                fontSize = 14.sp,
                color = Color.Black,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


@Composable
fun SettingItem(
    title: String,
    @DrawableRes icon: Int,
    iconTint: Color = Color(0xFF00B09C),
    iconBackground: Color = Color(0xFF00B09C).copy(alpha = 0.15f),
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(color = iconBackground, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = title,
            fontSize = 11.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}
