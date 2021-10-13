package com.example.audiomusicapp

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import com.example.audiomusicapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        this.window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.ivEminem.setOnClickListener(View.OnClickListener {
            startStopService()
        })
    }

    private fun startStopService() {
        if (isMyServiceRunning(MusicService::class.java)) {
            Toast.makeText(
                this,
                "Service Stopped",
                Toast.LENGTH_LONG
            ).show()

            stopService(Intent(this, MusicService::class.java))

        } else {
            Toast.makeText(
                this,
                "Service Started",
                Toast.LENGTH_LONG
            ).show()

            startService(Intent(this, MusicService::class.java))
        }


    }

    private fun isMyServiceRunning(mClass: Class<MusicService>): Boolean {

        val manager: ActivityManager = getSystemService(
            Context.ACTIVITY_SERVICE
        ) as ActivityManager

        for (service: ActivityManager.RunningServiceInfo in manager.getRunningServices(Integer.MAX_VALUE)) {

            if (mClass.name.equals(service.service.className)) {
                return true
            }
        }
        return false
    }
}