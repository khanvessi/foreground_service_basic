package com.example.audiomusicapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.audiomusicapp.Utils.Constants

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val musicChannel = NotificationChannel(Constants.MUSIC_CHANNEL_ID,
            "Music Channel",
            NotificationManager.IMPORTANCE_HIGH)

            musicChannel.description = "This is a channel for music player"

            val manager: NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

            manager.createNotificationChannel(musicChannel)
        }
    }
}