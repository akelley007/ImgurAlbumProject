package com.ajkelley.imguralbumproject.fragments

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.adapters.GalleryListAdapter
import com.ajkelley.imguralbumproject.databinding.FragmentAlbumDetailsBinding
import com.ajkelley.imguralbumproject.models.Album
import com.ajkelley.imguralbumproject.models.Image
import com.ajkelley.imguralbumproject.viewModels.AlbumsListViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import java.lang.Exception

class AlbumDetailsFragment: BaseFragment() {
    private lateinit var viewModel: AlbumsListViewModel
    private lateinit var binding: FragmentAlbumDetailsBinding
    private lateinit var galleryAdapter: GalleryListAdapter
    private lateinit var images: Array<Image>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        val coverUrl = AlbumDetailsFragmentArgs.fromBundle(arguments!!).coverImageUrl
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_album_details, null, false)
        setupActionBar("", binding.toolbar)

        viewModel = mActivity.viewModel!!
        binding.viewModel = viewModel
        binding.album = viewModel.selectedAlbum.value
        images = (viewModel.selectedAlbum.value as Album).images

        galleryAdapter = GalleryListAdapter(mActivity, images, this)
        binding.galleryGrid.layoutManager = GridLayoutManager(mActivity, 3)
        binding.galleryGrid.adapter = galleryAdapter


        //change the color of the progressBar to the primary app color
        binding.progressBar.indeterminateDrawable.setColorFilter(resources.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP)

        viewModel.onRetrieveInfoStart()
        //load the cover image
        Glide.with(context)
            .load(coverUrl.replace("\\", ""))
            .listener(object : RequestListener<String, GlideDrawable>{
                override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
                    viewModel.onRetrieveInfoFinish()
                    return false
                }

                override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                    viewModel.onRetrieveInfoFinish()
                    return false
                }

            })
            .thumbnail(0.1f)
            .dontAnimate()
            .placeholder(R.mipmap.image_placeholder)
            .error(R.mipmap.image_placeholder_error)
            .into(binding.ivAlbumCover)

        subscribeUi()
        return binding.root
    }

    override fun subscribeUi() {
        viewModel.loadingVisibility.observe(this, Observer{
                loadingVisibility -> setViewLoading(loadingVisibility == View.VISIBLE)
        })
    }

    //toggle loading view to hide view until image is loaded
    fun setViewLoading(loading: Boolean){
        enableUI(!loading)
        if(loading){
            binding.detailsProgressContainer.visibility = View.VISIBLE
        }
        else{
            binding.detailsProgressContainer.visibility = View.GONE
        }
    }


    fun onPhotoClicked(position: Int){
        val direction = AlbumDetailsFragmentDirections.actionDetailsFragmentToImageFragment(position)
        mActivity.navHostFragment.navController.navigate(direction)
    }
}