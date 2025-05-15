package com.apprication.astrofeed.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.apprication.astrofeed.model.ApodEntity
import com.apprication.astrofeed.repository.BookmarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookmarkViewModel(application: Application) : AndroidViewModel(application) {

    internal val repo = BookmarkRepository(application)

    private val _isBookmarked = MutableStateFlow(false)
    val isBookmarked: StateFlow<Boolean> = _isBookmarked

    fun checkBookmark(date: String) {
        viewModelScope.launch {
            _isBookmarked.value = repo.isBookmarked(date)
        }
    }

    fun toggleBookmark(apod: ApodEntity) {
        viewModelScope.launch {
            if (_isBookmarked.value) {
                repo.delete(apod)
            } else {
                repo.insert(apod)
            }
            checkBookmark(apod.date)
        }
    }
}