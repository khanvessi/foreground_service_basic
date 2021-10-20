package com.example.audiomusicapp.ui.main

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.audiomusicapp.data.MusicDatabase
import com.example.audiomusicapp.models.Track
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

//    @SuppressLint("StaticFieldLeak")
//    private val context = getApplication<Application>().applicationContext
    val dao = MusicDatabase.getDatabase(application).musicDao()
    var mBound = MutableLiveData<Boolean>()
    var playStopMusic = MutableLiveData<Boolean>()
    var playPause = MutableLiveData<Boolean>()

    val listOfTracks = MutableLiveData<List<Track>>()
    var listOfLiveDataTracks: LiveData<List<Track>> = listOfTracks

    var track = Track(track = "", artistPic = "", artistName = "")

    fun getListOfTracks(){
        viewModelScope.launch {
            listOfLiveDataTracks = dao.getAllTracks()
            Log.e("List of Tracks ", ""+listOfLiveDataTracks.value)
        }
    }
}