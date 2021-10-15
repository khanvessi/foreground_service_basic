package com.example.audiomusicapp.adapters

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import android.R

import com.bumptech.glide.request.RequestOptions




@BindingAdapter("imageFromUrl")
fun ImageView.imageFromUrl(url: String){
    Log.e("BindingAdapters", ""+url)
    val options: RequestOptions = RequestOptions()
        .centerCrop()
        .placeholder(R.drawable.stat_notify_error)
        .error(R.drawable.stat_notify_error)

    Glide.with(this.context).load(url).apply(options).into(this)
}