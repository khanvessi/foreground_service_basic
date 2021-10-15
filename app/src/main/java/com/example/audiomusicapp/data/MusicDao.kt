package com.example.audiomusicapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.audiomusicapp.models.Track

@Dao
interface MusicDao {

    @Query("SELECT * FROM tracks")
    fun getAllTracks(): LiveData<List<Track>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertTrack(track: Track?): Long
//

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListOfTracks(tracks: List<Track>): List<Long>
}