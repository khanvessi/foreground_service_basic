package com.example.audiomusicapp.services

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.audiomusicapp.R
import java.lang.UnsupportedOperationException

class ForegroundService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        showNotification()

        val thread = Thread {
            //TODO: WRITE ON STARTCOMMAND LOGIC

            stopForeground(true)
            stopSelf()
        }

        thread.start()

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(
            "ON DESTROY",
            "onDestroy: called"
        )
    }

    private fun showNotification() {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, "channelId")

        builder.setSmallIcon(R.mipmap.ic_launcher)
            .setContentText("This is a service notificaion")
            .setContentTitle("Title")
        val notification: Notification = builder.build()

        startForeground(123, notification)
    }
}