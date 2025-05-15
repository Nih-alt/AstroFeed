package com.apprication.astrofeed.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apprication.astrofeed.model.ApodResponse
import com.apprication.astrofeed.repository.MultiApodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class MultiApodViewModel : ViewModel() {

    private val repo = MultiApodRepository()
    private val _apods = MutableStateFlow<List<ApodResponse>>(emptyList())
    val apods: StateFlow<List<ApodResponse>> = _apods

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    init {
        loadLast10Days()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadLast10Days() {
        viewModelScope.launch {
            _loading.value = true
            try {
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val endDate = LocalDate.now()
                val startDate = endDate.minusDays(9)
                val list = repo.getApodList(
                    start = startDate.format(formatter),
                    end = endDate.format(formatter)
                ).reversed() // latest first
                _apods.value = list
            } catch (e: Exception) {
                // TODO: Handle error state
            } finally {
                _loading.value = false
            }
        }
    }
}