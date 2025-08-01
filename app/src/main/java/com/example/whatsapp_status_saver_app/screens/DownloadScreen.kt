package com.example.whatsapp_status_saver_app.screens

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.whatsapp_status_saver_app.R
import com.example.whatsapp_status_saver_app.dp.MainViewModel
import com.example.whatsapp_status_saver_app.dp.Repository
import com.example.whatsapp_status_saver_app.dp.StatusDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DownloadScreen(navController: NavController) {
    val context = LocalContext.current
    val statusDataBase = remember { StatusDataBase.getDataBase(context) }
    val repository = remember { Repository(statusDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    val downloads by viewModel.allDownloads.observeAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Back")
                    }
                },
                title = { Text("Downloads", fontSize = 20.sp, fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        if (downloads.isEmpty()) {
            Box(
                Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Download, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(64.dp))
                    Spacer(Modifier.height(8.dp))
                    Text("No downloads yet", color = Color.Gray)
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(downloads) { download ->
                    val file = File(download.filePath)
                    val isVideo = download.fileType == "video"

                    var videoThumbnail by remember { mutableStateOf<Bitmap?>(null) }

                    if (isVideo) {
                        LaunchedEffect(file.path) {
                            withContext(Dispatchers.IO) {
                                try {
                                    val retriever = MediaMetadataRetriever()

                                    val uri = FileProvider.getUriForFile(
                                        context,
                                        "${context.packageName}.provider",
                                        file
                                    )

                                    retriever.setDataSource(context, uri)
                                    val bmp = retriever.frameAtTime
                                    retriever.release()

                                    videoThumbnail = bmp
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.LightGray.copy(alpha = 0.1f))
                            .clickable {
                                navController.navigate(
                                    Screens.SingleViewScreen.route + "/${Uri.encode(file.absolutePath)}"
                                )
                            }
                    ) {
                        if (isVideo) {
                            if (videoThumbnail != null) {
                                Image(
                                    bitmap = videoThumbnail!!.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        } else {
                            AsyncImage(
                                model = file,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                contentScale = ContentScale.Crop
                            )
                        }

                        if (isVideo) {
                            Icon(
                                Icons.Default.PlayCircle,
                                contentDescription = "Play",
                                tint = Color.White,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
