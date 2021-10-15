package com.example.audiomusicapp.ui

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.audiomusicapp.services.MusicService
import com.example.audiomusicapp.adapters.ViewPagerAdapter
import com.example.audiomusicapp.data.MusicDatabase
import com.example.audiomusicapp.databinding.ActivityMainBinding
import com.example.audiomusicapp.models.Track
import com.example.audiomusicapp.ui.hiphop.HipHopFragment
import kotlinx.coroutines.launch

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

        val dao = MusicDatabase.getDatabase(this).musicDao()


        val listOfTracks = listOf(
            //EM
            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1600962815726-457c46a12681?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=388&q=80",

                artistName = "Eminem"
            ),

            //NF
            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1623531249239-07774b804ac8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=412&q=80",

                artistName = "NF"
            ),

            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1600962815726-457c46a12681?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=388&q=80",

                artistName = "Eminem"
            ),

            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1543379344-402b42ddbe8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1528&q=80",

                artistName = "2PAC"
            ),


            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1623531249239-07774b804ac8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=412&q=80",

                artistName = "NF"
            ),

            //2pac

            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1543379344-402b42ddbe8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1528&q=80",

                artistName = "2PAC"
            ),

            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1623531249239-07774b804ac8?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=412&q=80",

                artistName = "NF"
            ),


            //drake
            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1543379344-402b42ddbe8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1528&q=80",

                artistName = "Drake"
            ),

            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1543379344-402b42ddbe8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1528&q=80",

                artistName = "Drake"
            ),

            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1543379344-402b42ddbe8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1528&q=80",

                artistName = "2PAC"
            ),

            Track(
                track = "https://audiojungle.net/item/jazzy-boom-bap/34155691",
                artistPic = "https://images.unsplash.com/photo-1543379344-402b42ddbe8a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1528&q=80",

                artistName = "Drake"
            ),

            )

        lifecycleScope.launch {
            dao.insertListOfTracks(
                listOfTracks
            )
        }



        val fragmentAdapter = ViewPagerAdapter(supportFragmentManager)
        fragmentAdapter.addFragment(HipHopFragment(), "HipHop")
        fragmentAdapter.addFragment(PopFragment(), "Pop")
        fragmentAdapter.addFragment(RockFragment(), "Rock")

        binding.apply {
            viewPager.adapter = fragmentAdapter
            tablayout.setupWithViewPager(viewPager)
        }

//        binding.ivEminem.setOnClickListener(View.OnClickListener {
//            startStopService()
//        })
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