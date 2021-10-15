package com.example.audiomusicapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.audiomusicapp.models.Track

@Database(entities = [Track::class], version = 1)
abstract class MusicDatabase : RoomDatabase() {

    abstract fun musicDao():MusicDao

    companion object {
        private var INSTANCE: MusicDatabase? = null
        fun getDatabase(context: Context): MusicDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        MusicDatabase::class.java,
                        "tracks_database"
                    )
                        //.createFromAsset("quotes.db")
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}