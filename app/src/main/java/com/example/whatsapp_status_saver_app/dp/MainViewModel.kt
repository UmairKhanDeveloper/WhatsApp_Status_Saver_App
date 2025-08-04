package com.example.whatsapp_status_saver_app.dp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch


class MainViewModel(private val repository: Repository) : ViewModel() {

    val allDownloads: LiveData<List<StatusDownload>> = repository.getAllDownloads()

    fun insert(filePath: String, fileType: String) {
        viewModelScope.launch {
            repository.insert(
                StatusDownload(
                    filePath = filePath,
                    fileType = fileType
                )
            )
        }
    }

    fun deleteDownload(statusDownload: StatusDownload) {
        viewModelScope.launch {
            repository.delete(statusDownload)
        }
    }

    fun deleteAllDownloads() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }
}
