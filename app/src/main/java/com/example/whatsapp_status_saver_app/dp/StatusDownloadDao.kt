package com.example.whatsapp_status_saver_app.dp

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface StatusDownloadDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(statusDownload: StatusDownload)

    @Delete
    suspend fun delete(statusDownload: StatusDownload)

    @Query("SELECT * FROM statusdownload ORDER BY timeStamp DESC")
    fun getAllDownloads(): LiveData<List<StatusDownload>>

    @Query("DELETE FROM statusdownload")
    suspend fun deleteAll()
}
