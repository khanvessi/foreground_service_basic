package com.example.audiomusicapp.ui.hiphop

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HipHopViewModelFactory(val context: Context):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HipHopViewModel(context) as T
    }
}