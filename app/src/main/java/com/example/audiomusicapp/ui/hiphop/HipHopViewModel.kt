package com.example.audiomusicapp.ui.hiphop

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiomusicapp.data.MusicDatabase
import com.example.audiomusicapp.models.Track
import kotlinx.coroutines.launch

//TODO: JUST FOR CHECKING (IT'S A BAD PRACTICE)
class HipHopViewModel(val context: Context):ViewModel() {

    val dao = MusicDatabase.getDatabase(context).musicDao()
    var mBound = MutableLiveData<Boolean>()

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