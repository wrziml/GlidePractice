package com.tollcafe.glidepractice

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel (application: Application): AndroidViewModel(application) {
    private val _readItemId = MutableLiveData<Int>()
    val readItemId: LiveData<Int> get() = _readItemId

    fun markAsRead(id: Int) {
        _readItemId.value = id
    }
}
