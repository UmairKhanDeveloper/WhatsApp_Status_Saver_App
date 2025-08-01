package com.example.whatsapp_status_saver_app.screens

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.whatsapp_status_saver_app.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, countryCode: String?) {
    val context = LocalContext.current
    val prefsManager = remember { SharedPrefsManager(context) }

    val languageCode = remember {
        countryCode ?: prefsManager.getString("selectedCountryCode", "GB") ?: "GB"
    }

    var showRateUsDialog by remember { mutableStateOf(false) }

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
                        text = stringResource(id = R.string.Status_Up),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = stringResource(id = R.string.All_in_one_status_saver),
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
                    title = stringResource(id = R.string.WhatsApp),
                    icon = R.drawable.image_1
                ) { navController.navigate(Screens.StatusScreen.route) }

                SelectionWhats(
                    title = stringResource(id = R.string.WhatsApp_Business),
                    icon = R.drawable.image_2
                ) { navController.navigate(Screens.StatusScreen.route) }

                SelectionWhats(
                    title = stringResource(id = R.string.WhatsApp_Web),
                    icon = R.drawable.image_3
                ) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://web.whatsapp.com"))
                    context.startActivity(intent)

                }

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
                title = stringResource(id = R.string.Direct_Chat),
                icon = R.drawable.telegramlogo
            ) { navController.navigate(Screens.DirectChatScreen.route) }
            CardItem(title = stringResource(id = R.string.Saved_Files), icon = R.drawable.folder) {
                navController.navigate(Screens.DownloadScreen.route)
            }
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
                SettingItem(title = stringResource(id = R.string.Rate_US), icon = R.drawable.star) {
                    showRateUsDialog = true
                }
                SettingItem(
                    title = stringResource(id = R.string.Share_App),
                    icon = R.drawable.share
                ) {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "Check out this amazing app: https://play.google.com/store/apps/details?id=${context.packageName}"
                        )
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }

                SettingItem(
                    title = stringResource(id = R.string.Privacy_Policy),
                    icon = R.drawable.shield
                ) {
                    navController.navigate(Screens.PrivacyPolicy.route)
                }
            }
        }

        if (showRateUsDialog) {
            AlertDialog(
                onDismissRequest = { showRateUsDialog = false },
                confirmButton = {},
                dismissButton = {},
                text = {
                    RateUsDialogContent(
                        onRateClick = { showRateUsDialog = false },
                        onLaterClick = { showRateUsDialog = false }
                    )
                },
                shape = RoundedCornerShape(16.dp),
                containerColor = Color.White
            )
        }
    }
}

@Composable
fun RateUsDialogContent(onRateClick: () -> Unit, onLaterClick: () -> Unit) {
    var selectedRating by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(id = R.drawable.ic_thoumb),
            contentDescription = "Thumb",
            tint = Color(0xFF039840),
            modifier = Modifier.size(120.dp)
        )


        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Your Opinion Matter to us!",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stringResource(id = R.string.Tell_us_how_was_your_experience_with_status_up_app),
            fontSize = 14.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))


        Row(horizontalArrangement = Arrangement.Center) {
            repeat(5) { index ->
                Icon(

                    imageVector = if (index < selectedRating) Icons.Default.Star else Icons.Outlined.Star,
                    contentDescription = "Star",
                    tint = if (index < selectedRating) Color(0xFFFFC107) else Color.Gray,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { selectedRating = index + 1 }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onRateClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(25.dp)),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF039840))
        ) {
            Text(
                stringResource(id = R.string.Rate_Us),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = stringResource(id = R.string.Later),
            color = Color.Gray, fontSize = 24.sp,
            modifier = Modifier.clickable { onLaterClick() }
        )

        Spacer(modifier = Modifier.height(8.dp))
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
