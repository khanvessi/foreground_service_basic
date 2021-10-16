package com.example.audiomusicapp.services

import android.app.*
import android.content.Intent
import android.util.Log
import android.app.Notification

import android.app.Service
import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.audiomusicapp.R
import com.example.audiomusicapp.Utils.Constants
import com.example.audiomusicapp.ui.hiphop.HipHopFragment
import okhttp3.internal.notify
import java.lang.UnsupportedOperationException


class MusicService : Service() {

    val TAG = "MyTag"

    private val mBinder: Binder = LocalBinder()
    private var mPlayer: MediaPlayer? = null



    override fun onCreate() {
        super.onCreate()
        mPlayer = MediaPlayer.create(this, R.raw.music)

        mPlayer!!.setOnCompletionListener {
            val intent =
                Intent("music_complete")
            intent.putExtra("message_key", "done")
            LocalBroadcastManager.getInstance(applicationContext)
                .sendBroadcast(intent)
            stopForeground(true)
            stopSelf()
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            Constants.MUSIC_SERVICE_ACTION_PLAY -> {
                Log.d(
                   "on Start command",
                    "onStartCommand: play called"
                )
                play()
            }
            Constants.MUSIC_SERVICE_ACTION_PAUSE -> {
                Log.d(
                    "onStartCommand",
                    "onStartCommand: pause called"
                )
                pause()
            }
            Constants.MUSIC_SERVICE_ACTION_STOP -> {
                run {
                    Log.d(
                        "onStartCommand",
                        "onStartCommand: stop called"
                    )
                    stopForeground(true)
                    stopSelf()
                }
                run {
                    Log.d(
                        "onStartCommand",
                        "onStartCommand: start called"
                    )
                    showNotification()
                }
            }
            Constants.MUSIC_SERVICE_ACTION_START -> {
                Log.d(
                    "onStartCommand",
                    "onStartCommand: start called"
                )
                showNotification()
            }
            else -> {
            }
        }
        Log.d("onStartCommand", "onStartCommand: ")
        return START_NOT_STICKY
    }




    @RequiresApi(Build.VERSION_CODES.N)
    private fun showNotification() {
        val manager = getSystemService(NOTIFICATION_SERVICE)
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, Constants.MUSIC_CHANNEL_ID)
        //Intent for play button
        val pIntent =
            Intent(this, MusicService::class.java)
        pIntent.action = Constants.MUSIC_SERVICE_ACTION_PLAY
        val playIntent = PendingIntent.getService(this, 100, pIntent, 0)

        //Intent for pause button
        val psIntent =
            Intent(this, MusicService::class.java)

        psIntent.action = Constants.MUSIC_SERVICE_ACTION_PAUSE
        val pauseIntent = PendingIntent.getService(this, 100, psIntent, 0)

        //Intent for stop button
        val sIntent =
            Intent(this, MusicService::class.java)

        sIntent.action = Constants.MUSIC_SERVICE_ACTION_STOP
        val stopIntent = PendingIntent.getService(this, 100, sIntent, 0)
        builder.setContentTitle("Music Player")
            .setContentText("This is demo music player")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_play,
                    "Play",
                    playIntent
                )
            )
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_play,
                    "Pause",
                    pauseIntent
                )
            )
            .addAction(
                NotificationCompat.Action(
                    android.R.drawable.ic_media_play,
                    "Stop",
                    stopIntent
                )
            )
        startForeground(123, builder.build())
        //manager.notify()
    }

    @Nullable
    override fun onBind(intent: Intent?): IBinder? {
        Log.d("onBind", "onBind: ")

        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d("onUnbind", "onUnbind: ")
        return true
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
        Log.d("onUnbind", "onRebind: ")

    }

    override fun onDestroy() {
        Log.d("onUnbind", "onDestroy: ")

        super.onDestroy()
        mPlayer!!.release()
    }


    //public client methods
    fun isPlaying(): Boolean {
        return mPlayer!!.isPlaying
    }


    fun play() {
        mPlayer!!.start()
    }

    fun pause() {
        mPlayer!!.pause()
    }

}