package com.example.whatsapp_status_saver_app.screens

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.whatsapp_status_saver_app.R

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SingleViewScreen(navController: NavController) {
    Scaffold(topBar = {
        TopAppBar(title = {

        }, navigationIcon = {
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
                    text = "Video",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        })
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.video_data),
                contentDescription = "Video Preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(550.dp)
                    .clip(RoundedCornerShape(6.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ActionIconButton(
                    icon = Icons.Default.Whatsapp,
                    contentDescription = "Share on WhatsApp"
                )
                ActionIconButton(
                    icon = Icons.Default.Share,
                    contentDescription = "Share"
                )
                ActionIconButton(
                    icon = Icons.Default.Download,
                    contentDescription = "Download"
                )
            }
        }

    }

}

@Composable
fun ActionIconButton(
     icon:ImageVector ,
    contentDescription: String
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(color = Color(0xFF00B09C), shape = CircleShape)
            .clickable {  },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}