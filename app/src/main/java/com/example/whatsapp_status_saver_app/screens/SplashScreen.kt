package com.example.whatsapp_status_saver_app.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsapp_status_saver_app.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {
    val context = LocalContext.current
    val prefsManager = remember { SharedPrefsManager(context) }
    val scale = remember { androidx.compose.animation.core.Animatable(0f) }

    val isLanguageSelected = prefsManager.getBoolean("isLanguageSelected", false)

    LaunchedEffect(Unit) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = { OvershootInterpolator(4f).getInterpolation(it) }
            )
        )
        delay(2000)

        if (isLanguageSelected) {
            val savedCountryCode = prefsManager.getString("selectedCountryCode", "GB") ?: "GB"
            navController.navigate(Screens.HomeScreen.route + "/$savedCountryCode") {
                popUpTo(0)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF001F2D),
                        Color(0xFF003C3C)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.splash),
                contentDescription = null,
                modifier = Modifier
                    .scale(scale.value)
                    .size(200.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Status up",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(10.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF00C3A6))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Status Saver",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .height(2.dp)
                        .width(10.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(Color(0xFF00C3A6))
                )
            }

            Spacer(modifier = Modifier.height(200.dp))

            if (!isLanguageSelected) {
                Button(
                    onClick = {
                        navController.navigate(Screens.LanguageSelectionScreen.route) {
                            popUpTo(0)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00C3A6)
                    ),
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp)
                ) {
                    Text(
                        text = "Get Started",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
