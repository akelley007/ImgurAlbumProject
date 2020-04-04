package com.ajkelley.imguralbumproject.fragments

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.activities.MainActivity


open class BaseFragment: Fragment() {
    lateinit var mActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as MainActivity


        // This callback will only be called when MyFragment is at least Started.
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    if(this@BaseFragment is AlbumsListFragment){
                        mActivity.finish()
                    }
                    else{
                        mActivity.navHostFragment.navController.popBackStack()
                    }
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    fun setupActionBar(title: String, toolbar: Toolbar){
        toolbar.setTitle(title)
        toolbar.setNavigationOnClickListener {
            mActivity.onBackPressed()
        }
        mActivity.setSupportActionBar(toolbar)
        mActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mActivity.supportActionBar?.setHomeButtonEnabled(true)
    }


    //function to be overwritten in extended fragments
    open fun subscribeUi(){

    }

    fun enableUI(enabled: Boolean) {
        if (enabled) {
            mActivity.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        } else {
            mActivity.window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        }
    }
}