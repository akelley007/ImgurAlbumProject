package com.ajkelley.imguralbumproject.adapters

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.databinding.AlbumListItemBinding
import com.ajkelley.imguralbumproject.models.Album
import com.ajkelley.imguralbumproject.viewModels.AlbumsListViewModel
import com.bumptech.glide.Glide
import java.net.URLEncoder

class AlbumsListAdapter(private val context: Context, private val viewModel: AlbumsListViewModel): RecyclerView.Adapter<AlbumsListAdapter.AlbumViewHolder>() {
    var albums: ArrayList<Album> = ArrayList()


    fun updateList(albums: ArrayList<Album>){
        this.albums = albums
        notifyDataSetChanged()
        viewModel.onRetrieveInfoFinish()
    }

    class AlbumViewHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener{
        var binding: AlbumListItemBinding?
        var albumCoverUrl = ""

        init{
            v.setOnClickListener(this)
            binding = DataBindingUtil.bind(v)
        }

        override fun onClick(v: View?) {
            binding?.viewModel?.onAlbumSelected(binding?.album!!, albumCoverUrl)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(LayoutInflater.from(context).inflate(R.layout.album_list_item, parent, false))
    }

    override fun getItemCount(): Int {
       return albums.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums.get(position)
        var albumCoverImage = ""
        //set databinding variable to fill in view
        holder.binding?.album = album
        holder.binding?.viewModel = viewModel
        //find the image within the images that matches the cover id
        for(image in album.images){
            if(image.id.equals(album.cover)){
                albumCoverImage = image.link
            }
        }

        //use glide to load the album cover image
        if(!TextUtils.isEmpty(albumCoverImage)){
            holder.albumCoverUrl = albumCoverImage.replace("\\", "")
            Glide.with(context)
                .load(holder.albumCoverUrl)
                .thumbnail(0.1f)
                .dontAnimate()
                .placeholder(R.mipmap.image_placeholder)
                .error(R.drawable.ic_broken_image)
                .into(holder.binding?.ivAlbumCover)
        }
        else{
            holder.binding?.ivAlbumCover?.setBackgroundResource(R.drawable.ic_broken_image)
        }
    }

}