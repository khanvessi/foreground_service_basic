package com.example.audiomusicapp

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi

class MusicService : Service() {

    private lateinit var musicPlayer: MediaPlayer
    val CHANNEL_ID = "my_channel_id"
    val MUSIC_NOTIFICATION_ID = 111

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        startMusic()
        createMusicNotificationChannel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        showNotification()
        musicPlayer.start()
        return START_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,0, notificationIntent,0
        )

        val notification = Notification
            .Builder(this, CHANNEL_ID)
            .setContentText("HipHop Music")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentIntent(pendingIntent)
            .build()

        startForeground(MUSIC_NOTIFICATION_ID, notification)
    }

    private fun createMusicNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val serviceChannel = NotificationChannel(
                CHANNEL_ID, "My Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun startMusic() {
        musicPlayer = MediaPlayer.create(this, R.raw.music)

        musicPlayer.isLooping = true
        musicPlayer.setVolume(100f,100f)
    }
}