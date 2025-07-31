package com.example.whatsapp_status_saver_app.screens

import android.Manifest
import android.content.Intent
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Size
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabTitles = listOf("Photos", "Videos")
    var selectedApp by remember { mutableStateOf("WhatsApp") }
    var statusFiles by remember { mutableStateOf(listOf<File>()) }
    var permissionGranted by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { perms ->
        if (perms.values.all { it }) {
            permissionGranted = true
        } else {
            Toast.makeText(context, "Permission Required!", Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                    .setData(Uri.parse("package:${context.packageName}"))
                context.startActivity(intent)
            } else {
                permissionGranted = true
            }
        } else {
            permissionLauncher.launch(
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    arrayOf(
                        Manifest.permission.READ_MEDIA_IMAGES,
                        Manifest.permission.READ_MEDIA_VIDEO
                    )
                } else {
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            )
        }
    }

    LaunchedEffect(permissionGranted, selectedApp) {
        if (permissionGranted) {
            statusFiles = loadStatuses(selectedApp)
        }
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
                        Text("Status", color = Color.Black, fontSize = 20.sp)
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

            val filteredFiles = statusFiles.filter {
                if (selectedTabIndex == 0) {
                    it.extension.lowercase() in listOf("jpg", "jpeg", "png", "webp")
                } else {
                    it.extension.lowercase() in listOf("mp4", "3gp")
                }
            }

            if (filteredFiles.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No Status Found", color = Color.Gray)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(filteredFiles) { file ->
                        if (selectedTabIndex == 0) {
                            // Photos
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
                            // Videos
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
            Text("No Preview", color = Color.White, fontSize = 12.sp)
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


fun loadStatuses(app: String): List<File> {
    val path = if (app == "WhatsApp")
        "/storage/emulated/0/Android/media/com.whatsapp/WhatsApp/Media/.Statuses"
    else
        "/storage/emulated/0/Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses"

    val dir = File(path)
    return if (dir.exists()) {
        dir.listFiles()?.filter { it.isFile }?.sortedByDescending { it.lastModified() }
            ?: emptyList()
    } else emptyList()
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
