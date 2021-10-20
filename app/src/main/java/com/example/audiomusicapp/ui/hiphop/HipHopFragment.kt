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
import androidx.activity.viewModels
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
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
import com.example.audiomusicapp.ui.main.MainViewModel


class HipHopFragment : Fragment() {

    lateinit var binding: FragmentHipHopBinding
    lateinit var hipHopViewModel: HipHopViewModel
    private var mMusicPlayerService: MusicService? = null
    val MESSAGE_KEY = "message_key"
    val mainViewModel by activityViewModels<MainViewModel>()

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
        val musicAdapter = MusicAdapter(MusicItemClickListener { imageBtn ->
            Log.e("onPlay", "CLICKED")
            mainViewModel.playStopMusic.value = true
            if(mainViewModel.playPause.value == true) imageBtn.setBackgroundResource(R.drawable.ic_pause_music)
            else imageBtn.setBackgroundResource(R.drawable.ic_play_music)
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

}