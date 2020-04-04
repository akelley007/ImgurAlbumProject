package com.ajkelley.imguralbumproject.fragments

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.adapters.ViewPagerAdapter
import com.ajkelley.imguralbumproject.databinding.FragmentViewPhotoBinding
import com.ajkelley.imguralbumproject.models.Album
import com.ajkelley.imguralbumproject.models.Image
import com.ajkelley.imguralbumproject.views.ZoomOutPageTransformer

class FullscreenFragment: BaseFragment() {
    private lateinit var binding: FragmentViewPhotoBinding
    private lateinit var images: Array<Image>
    private var numPages: Int = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_view_photo, null, false)
        setupActionBar("", binding.toolbar)

        images = (mActivity.viewModel!!.selectedAlbum.value as Album).images
        numPages = images.size
        binding.photoPager.adapter = ViewPagerAdapter(mActivity, images)
        binding.photoPager.setPageTransformer(ZoomOutPageTransformer())
        binding.photoPager.setCurrentItem(FullscreenFragmentArgs.fromBundle(arguments!!).ImageIndex, true)

        //set page change listener to update the toolbar title
        binding.photoPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                updateActionBar(position)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })
        return binding.root
    }

    fun updateActionBar(pos: Int){
        binding.toolbar.setTitle(String.format("Image %d of %d", pos + 1, numPages))
    }
}