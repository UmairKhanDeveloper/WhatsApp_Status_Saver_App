package com.example.whatsapp_status_saver_app.dp

import androidx.lifecycle.LiveData


class Repository(private val statusDataBase: StatusDataBase) {


    fun getAllDownloads(): LiveData<List<StatusDownload>> =
        statusDataBase.getDao().getAllDownloads()

    suspend fun insert(statusDownload: StatusDownload) {
        statusDataBase.getDao().insert(statusDownload)
    }

    suspend fun delete(statusDownload: StatusDownload) {
        statusDataBase.getDao().delete(statusDownload)
    }
}
