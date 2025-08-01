package com.example.whatsapp_status_saver_app.dp

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class StatusDownload(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val filePath: String,
    val fileType: String,
    val timeStamp: Long = System.currentTimeMillis()
)