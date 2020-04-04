package com.ajkelley.imguralbumproject.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.databinding.FullscreenImageLayoutBinding
import com.ajkelley.imguralbumproject.models.Image
import com.bumptech.glide.Glide
import java.net.URLEncoder

class ViewPagerAdapter(var context: Context, var images: Array<Image>): RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {


    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        var binding: FullscreenImageLayoutBinding?

        init{
            binding = DataBindingUtil.bind(v)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.fullscreen_image_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewPagerAdapter.ViewHolder, position: Int) {
        val image = images.get(position)

        //use glide to load the album cover image
        if(!TextUtils.isEmpty(image.link)){
            Glide.with(context)
                .load(image.link.replace("\\", ""))
                .thumbnail(0.1f)
                .dontAnimate()
                .placeholder(R.mipmap.image_placeholder)
                .error(R.drawable.ic_broken_image)
                .into(holder.binding?.imageView)
        }
        else{
            holder.binding?.imageView?.setBackgroundResource(R.drawable.ic_broken_image)
        }
    }
}