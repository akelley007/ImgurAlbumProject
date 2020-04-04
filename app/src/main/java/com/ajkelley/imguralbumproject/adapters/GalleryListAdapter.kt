package com.ajkelley.imguralbumproject.adapters

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.databinding.GalleryGridItemBinding
import com.ajkelley.imguralbumproject.fragments.AlbumDetailsFragment
import com.ajkelley.imguralbumproject.models.Image
import com.ajkelley.imguralbumproject.viewModels.AlbumsListViewModel
import com.bumptech.glide.Glide

class GalleryListAdapter (private val context: Context, private val images: Array<Image>, var fragment: AlbumDetailsFragment): RecyclerView.Adapter<GalleryListAdapter.ImageViewHolder>() {

    class ImageViewHolder(v: View, var fragment: AlbumDetailsFragment): RecyclerView.ViewHolder(v), View.OnClickListener{
        var binding: GalleryGridItemBinding?

        init{
            v.setOnClickListener(this)
            binding = DataBindingUtil.bind(v)
        }

        override fun onClick(v: View?) {
            fragment.onPhotoClicked(v!!.tag as Int)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(context).inflate(R.layout.gallery_grid_item, parent, false), fragment)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images.get(position)
        holder.binding?.root?.tag = position

        //use glide to load the album cover image
        if(!TextUtils.isEmpty(image.link)){
            Glide.with(context)
                .load(image.link)
                .thumbnail(0.1f)
                .dontAnimate()
                .placeholder(R.mipmap.image_placeholder)
                .error(R.drawable.ic_broken_image)
                .into(holder.binding?.ivImage)
        }
        else{
            holder.binding?.ivImage?.setBackgroundResource(R.drawable.ic_broken_image)
        }
    }

}