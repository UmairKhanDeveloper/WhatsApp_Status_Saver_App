package com.example.whatsapp_status_saver_app.screens

import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Size
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.whatsapp_status_saver_app.R
import kotlinx.coroutines.delay
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusScreen(navController: NavController, appType: String?) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf(stringResource(id = R.string.Photos), stringResource(id = R.string.Videos))

    var selectedApp by remember { mutableStateOf(appType ?: "WhatsApp") }

    var statusFiles by remember { mutableStateOf(listOf<File>()) }

    LaunchedEffect(selectedApp) {
        delay(100)
        statusFiles = loadStatuses(selectedApp)
    }

    Scaffold(
        topBar = {
            TopAppBar(
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
                                .background(Color(0XFF039840).copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Back",
                                tint = Color(0XFF039840)
                            )
                        }
                        Spacer(Modifier.width(20.dp))
                        Text(stringResource(id = R.string.Status), color = Color.Black, fontSize = 20.sp)
                    }
                },
                title = {}
            )
        }
    ) {
        Column(Modifier.padding(top = it.calculateTopPadding())) {
            WhatsAppToggle(selectedOption = selectedApp) { app ->
                selectedApp = app
            }

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
                                title,
                                color = if (selectedTabIndex == index) Color(0XFF039840) else Color.Gray
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            val filteredFiles = statusFiles.filter { file ->
                val extension = file.extension.lowercase()
                if (selectedTabIndex == 0) {
                    extension in listOf("jpg", "jpeg", "png", "webp")
                } else {
                    extension in listOf("mp4", "3gp")
                }
            }

            if (filteredFiles.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(id = R.string.No_Status_Found), color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(filteredFiles) { file ->
                        if (selectedTabIndex == 0) {
                            AsyncImage(
                                model = file,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .padding(2.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clickable {
                                        navController.navigate(
                                            Screens.SingleViewScreen.route + "/${Uri.encode(file.absolutePath)}"
                                        )
                                    }
                            )
                        } else {
                            VideoThumbnail(
                                file = file,
                                onClick = {
                                    navController.navigate(
                                        Screens.SingleViewScreen.route + "/${Uri.encode(file.absolutePath)}"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VideoThumbnail(file: File, onClick: () -> Unit) {
    val context = LocalContext.current
    val bitmap by remember(file.path) {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ThumbnailUtils.createVideoThumbnail(
                    file,
                    Size(200, 200),
                    null
                )
            } else {
                ThumbnailUtils.createVideoThumbnail(
                    file.path,
                    MediaStore.Video.Thumbnails.MINI_KIND
                )
            }
        )
    }

    Box(
        Modifier
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .fillMaxWidth()
            .height(120.dp)
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        } ?: Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Gray),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(id = R.string.No_Preview), color = Color.White, fontSize = 12.sp)
        }

        Icon(
            Icons.Default.PlayArrow,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .size(36.dp)
                .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                .padding(4.dp)
        )
    }
}


fun loadStatuses(appIdentifier: String): List<File> {
    val path = if (appIdentifier == "WhatsApp_Business") {
        "/storage/emulated/0/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses"
    } else {
        "/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/.Statuses"
    }

    val dir = File(path)
    return if (dir.exists()) {
        dir.listFiles()
            ?.filter { it.isFile && !it.name.endsWith(".nomedia") }
            ?.sortedByDescending { it.lastModified() }
            ?: emptyList()
    } else {
        emptyList()
    }
}


@Composable
fun WhatsAppToggle(
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    val options = mapOf(
        "WhatsApp" to stringResource(id = R.string.WhatsApp),
        "WhatsApp_Business" to stringResource(id = R.string.WhatsApp_Business)
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
    ) {
        options.forEach { (identifier, displayText) ->
            val isSelected = identifier == selectedOption
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(
                        if (isSelected) Color(0xFF00BFA5) else Color.Transparent
                    )
                    .clickable { onOptionSelected(identifier) }
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayText,
                    color = if (isSelected) Color.White else Color.Gray,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
            }
        }
    }
}