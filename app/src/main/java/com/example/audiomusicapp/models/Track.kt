package com.example.audiomusicapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tracks")
data class Track(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val track: String,
    val artistPic: String,
    val artistName: String
//    val trackName: String,
//    val artist: String,
//    val trackPath: String,
//    val isHipHop: Boolean,
//    val isPop: Boolean,
//    val isRock: Boolean
) {

}