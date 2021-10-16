package com.example.audiomusicapp.ui.hiphop

import android.app.ActivityManager
import android.content.*
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import com.example.audiomusicapp.MusicViewModel
import com.example.audiomusicapp.services.MusicService
import com.example.audiomusicapp.R
import com.example.audiomusicapp.Utils.Constants
import com.example.audiomusicapp.adapters.MusicAdapter
import com.example.audiomusicapp.adapters.MusicItemClickListener
import com.example.audiomusicapp.databinding.FragmentHipHopBinding


class HipHopFragment : Fragment() {

    lateinit var binding: FragmentHipHopBinding
    lateinit var hipHopViewModel: HipHopViewModel
    private var mMusicPlayerService: MusicService? = null
    val MESSAGE_KEY = "done"
    val musicAdapter = MusicAdapter()
    var isPlaying = MutableLiveData<Boolean>(true)

    //SERVICECONNECTION
    private val mServiceCon: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, iBinder: IBinder) {
            val myServiceBinder: MusicService.LocalBinder =
                iBinder as MusicService.LocalBinder
            mMusicPlayerService = myServiceBinder.getService()
            hipHopViewModel.mBound.value = true
            Log.d("on service disconnected", "onServiceConnected")
            if (mMusicPlayerService!!.isPlaying()) {
                //TODO: SET THIS BUTTON TEXT
                isPlaying.value = true
            }
        }

        override fun onServiceDisconnected(name: ComponentName) {
            Log.d("on Service connected", "onServiceDisconnected")
            hipHopViewModel.mBound.value = false
        }
    }
    //BRAODCAST RECEIVER
    private var mReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
//            String songName=intent.getStringExtra(MESSAGE_KEY);
            val result = intent.getStringExtra(MESSAGE_KEY)
            //TODO: CHANGE BUTTON TEXT
            if (result === "done") isPlaying.value = false
            Log.d("onReceive", "onReceive: Thread name: " + Thread.currentThread().name)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_hip_hop, container, false
        )
        hipHopViewModel = ViewModelProvider(this, HipHopViewModelFactory(requireContext())).get(HipHopViewModel::class.java)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ONPLAY CALLED HERE
        val musicAdapter = MusicAdapter(MusicItemClickListener {
            Log.e("onPlay", "CLICKED")
            if(isPlaying.value == true)
            {
                it.setBackgroundResource(R.drawable.ic_pause_music)
                isPlaying.value = false
            }
            else
            {
                it.setBackgroundResource(R.drawable.ic_play_music)
                isPlaying.value = true
            }
            startStopService()
        })

        binding.apply {
            recHipHop.apply {
                adapter = musicAdapter
                layoutManager = GridLayoutManager(requireContext(),2)
                setHasFixedSize(true)
            }
        }

        hipHopViewModel.getListOfTracks()

        hipHopViewModel.listOfLiveDataTracks.observe(viewLifecycleOwner){
            Log.e("Tracks ", ""+it)
            musicAdapter.submitList(it)
        }
    }

    private fun startStopService() {

        if (hipHopViewModel.mBound.value == true) {
            if (mMusicPlayerService?.isPlaying() == true) {
                mMusicPlayerService!!.pause()
                //TODO: CHANGE BUTTON TEXT
                isPlaying.value = true
            } else {
                val intent = Intent(requireContext(), MusicService::class.java)
                intent.action = Constants.MUSIC_SERVICE_ACTION_START
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    requireContext().startForegroundService(intent)
                } else {
                    requireContext().startService(intent)
                }
                mMusicPlayerService?.play()
                //TODO: CHANGE BUTTON TEXT
                isPlaying.value = false
            }
        }


    }

    override fun onStart() {
        super.onStart()
        Log.d("onStart", "onStart: called")
        val intent = Intent(requireContext(), MusicService::class.java)
        requireContext().bindService(intent, mServiceCon, Context.BIND_AUTO_CREATE)

        //TODO: DO BROADCASTING LATER
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(mReceiver, IntentFilter(Constants.MUSIC_COMPLETE))
    }

    override fun onStop() {
        super.onStop()
        if (hipHopViewModel.mBound.value == true) {
            requireContext().unbindService(mServiceCon)
            hipHopViewModel.mBound.value = false
        }
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(mReceiver)
    }
}