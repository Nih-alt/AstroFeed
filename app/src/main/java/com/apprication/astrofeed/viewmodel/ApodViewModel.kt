package com.apprication.astrofeed.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprication.astrofeed.model.ApodResponse
import com.apprication.astrofeed.repository.ApodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApodViewModel : ViewModel() {
    private val repository = ApodRepository()

    private val _apodData = MutableStateFlow<ApodResponse?>(null)
    val apodData: StateFlow<ApodResponse?> = _apodData

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchTodayApod()
    }

    private fun fetchTodayApod() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val data = repository.getTodayApod()
                _apodData.value = data
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load APOD"
            } finally {
                _isLoading.value = false
            }
        }
    }
}