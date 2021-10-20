package com.example.audiomusicapp.ui

import android.app.ActivityManager
import android.content.*
import android.content.ContentValues.TAG
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.audiomusicapp.Utils.Constants
import com.example.audiomusicapp.services.MusicService
import com.example.audiomusicapp.adapters.ViewPagerAdapter
import com.example.audiomusicapp.data.MusicDatabase
import com.example.audiomusicapp.databinding.ActivityMainBinding
import com.example.audiomusicapp.models.Track
import com.example.audiomusicapp.ui.hiphop.HipHopFragment
import com.example.audiomusicapp.ui.main.MainViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var mMusicPlayerService: MusicService? = null
    val mainViewModel by viewModels<MainViewModel>()


    //SERVICECONNECTION
    private val mServiceCon: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
            val myServiceBinder: MusicService.LocalBinder =
                iBinder as MusicService.LocalBinder
            mMusicPlayerService = myServiceBinder.getService()
            mainViewModel.mBound.value = true
            Log.d("on service disconnected", "onServiceConnected")
            if (mMusicPlayerService!!.isPlaying()) {
                //TODO: SET THIS BUTTON TEXT
                //mPlayButton.setText("Pause")
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d("on Service connected", "onServiceDisconnected")
            mainViewModel.mBound.value = false
        }
    }

    //BRAODCAST RECEIVER
    private var mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            String songName=intent.getStringExtra(MESSAGE_KEY);
            val result = intent.getStringExtra(Constants.MESSAGE_KEY)
            //TODO: CHANGE BUTTON TEXT
            //if (result === "done") mPlayButton.setText("Play")

            Log.d("onReceive", "onReceive: Thread name: " + Thread.currentThread().name)
        }
    }


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


        mainViewModel.playStopMusic.observe(this,){
            if(it) {
                startStopService()
                Log.e(TAG, "OBSERVE: CALLED", )
            }
        }
    }


    private fun startStopService() {
        if (mainViewModel.mBound.value == true) {
            if (mMusicPlayerService?.isPlaying() == true) {
                mMusicPlayerService!!.pause()
                //TODO: CHANGE BUTTON TEXT
                //mPlayButton.setText("Play")
                mainViewModel.playPause.value = false
            } else {
                val intent = Intent(this, MusicService::class.java)
                intent.action = Constants.MUSIC_SERVICE_ACTION_START
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                   startForegroundService(intent)
                } else {
                    startService(intent)
                }
                mMusicPlayerService?.play()
                //TODO: CHANGE BUTTON TEXT
                //mPlayButton.setText("Play")
                mainViewModel.playPause.value = true
            }
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("onStart", "onStart: called")
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, mServiceCon, Context.BIND_AUTO_CREATE)

        //TODO: DO BROADCASTING LATER
//        LocalBroadcastManager.getInstance(requireContext())
//            .registerReceiver(mReceiver, IntentFilter(MusicService.MUSIC_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        if (mainViewModel.mBound.value == true) {
            unbindService(mServiceCon)
            mainViewModel.mBound.value = false
        }
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mReceiver)
    }
}