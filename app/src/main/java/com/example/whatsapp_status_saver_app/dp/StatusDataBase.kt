package com.example.whatsapp_status_saver_app.dp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [StatusDownload::class], version = 1, exportSchema = false)
abstract class StatusDataBase : RoomDatabase() {
    abstract fun getDao(): StatusDownloadDao

    companion object {
        @Volatile
        private var INSTANCE: StatusDataBase? = null

        fun getDataBase(context: Context): StatusDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StatusDataBase::class.java,
                    "status_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
