package com.example.whatsapp_status_saver_app.screens

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Environment
import android.widget.Toast
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.whatsapp_status_saver_app.R
import com.example.whatsapp_status_saver_app.dp.MainViewModel
import com.example.whatsapp_status_saver_app.dp.Repository
import com.example.whatsapp_status_saver_app.dp.StatusDataBase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SingleViewScreen(navController: NavController, filePath: String?) {
    val context = LocalContext.current
    val statusDataBase = remember { StatusDataBase.getDataBase(context) }
    val repository = remember { Repository(statusDataBase) }
    val viewModel = remember { MainViewModel(repository) }

    val file = filePath?.let { File(it) }
    var thumbnail by remember { mutableStateOf<Bitmap?>(null) }

    fun downloadStatus() {
        file?.let {
            try {
                val downloadsDir = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "StatusSaver"
                )
                if (!downloadsDir.exists()) downloadsDir.mkdirs()

                val destFile = File(downloadsDir, it.name)
                it.copyTo(destFile, overwrite = true)

                val type = if (it.extension.lowercase() in listOf(
                        "jpg",
                        "jpeg",
                        "png",
                        "webp"
                    )
                ) "image" else "video"
                viewModel.insert(destFile.absolutePath, type)

                val dm = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
                val uri = Uri.fromFile(destFile)

                val request = DownloadManager.Request(uri)
                    .setTitle(destFile.name)
                    .setDescription("Saved to Downloads/StatusSaver")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // ✅ Show notification
                    .setAllowedOverMetered(true)
                    .setAllowedOverRoaming(true)

                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS + "/StatusSaver",
                    destFile.name
                )

                dm.enqueue(request)

                Toast.makeText(context, "Status saved successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Download successfully", Toast.LENGTH_SHORT).show()
            }
        }
    }


    LaunchedEffect(file?.path) {
        if (file != null && file.extension.lowercase() in listOf("mp4", "3gp")) {
            withContext(Dispatchers.IO) {
                try {
                    val retriever = MediaMetadataRetriever()
                    retriever.setDataSource(file.absolutePath)
                    thumbnail = retriever.getFrameAtTime(0)
                    retriever.release()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.View_Status)) },
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
                        Text(
                            stringResource(id = R.string.Status),
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (file != null && file.exists()) {
                    when (file.extension.lowercase()) {
                        in listOf("jpg", "jpeg", "png", "webp") -> {
                            AsyncImage(
                                model = file,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }

                        in listOf("mp4", "3gp") -> {
                            if (thumbnail != null) {
                                Image(
                                    bitmap = thumbnail!!.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.FillWidth
                                )
                                Icon(
                                    Icons.Default.PlayArrow,
                                    contentDescription = "Play Video",
                                    tint = Color.White,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                                        .padding(8.dp)
                                        .clickable {
                                            navController.navigate(
                                                Screens.ExoPlayer.route + "/${
                                                    Uri.encode(
                                                        file.absolutePath
                                                    )
                                                }"
                                            )
                                        }
                                )
                            } else CircularProgressIndicator()
                        }

                        else -> Text(
                            stringResource(id = R.string.Unsupported_File),
                            color = Color.Gray
                        )
                    }
                } else {
                    Text(stringResource(id = R.string.File_not_found), color = Color.Gray)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ActionIconButton(Icons.Default.Whatsapp, "WhatsApp") { }
                ActionIconButton(Icons.Default.Share, "Share") {}
                ActionIconButton(Icons.Default.Download, "Download") { downloadStatus() }
            }
        }
    }
}


@Composable
fun ActionIconButton(icon: ImageVector, contentDescription: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .background(color = Color(0xFF00B09C), CircleShape)
            .clickable { onClick() },
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