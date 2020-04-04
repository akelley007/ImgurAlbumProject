package com.ajkelley.imguralbumproject.fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ajkelley.imguralbumproject.R
import com.ajkelley.imguralbumproject.adapters.AlbumsListAdapter
import com.ajkelley.imguralbumproject.databinding.FragmentAlbumsListBinding
import com.ajkelley.imguralbumproject.extensions.getViewModel
import com.ajkelley.imguralbumproject.models.Album
import com.ajkelley.imguralbumproject.viewModels.AlbumsListViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlin.collections.ArrayList

class AlbumsListFragment: BaseFragment(),  SwipeRefreshLayout.OnRefreshListener, MaterialSearchBar.OnSearchActionListener{
    private lateinit var binding: FragmentAlbumsListBinding
    private lateinit var viewModel: AlbumsListViewModel
    private lateinit var adapter: AlbumsListAdapter
    private var lastQuery: String = ""
    val BUTTON_SPEECH = 1;
    val BUTTON_BACK = 3;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_albums_list, null, false)

        //retrieve the viewModel if it does not yet exist, otherwise reuse the one in the activity
        if(mActivity.viewModel == null){
            viewModel = getViewModel()
            binding.viewModel = viewModel
            mActivity.viewModel = viewModel
        }
        else{
            binding.viewModel = mActivity.viewModel
            updateList(viewModel.albums.value)
            //reset selected album to nothing
            binding.viewModel!!.selectedAlbum.value = null
        }

        if(resources.getBoolean(R.bool.isTablet)){
            binding.rvAlbums.layoutManager = GridLayoutManager(mActivity, 3)
        }
        else{
            binding.rvAlbums.layoutManager = GridLayoutManager(mActivity, 2)
        }

        adapter = AlbumsListAdapter(mActivity, viewModel)
        binding.rvAlbums.setAdapter(adapter)

        binding.swipeContainer.setOnRefreshListener(this)
        binding.swipeContainer.setColorScheme(R.color.colorPrimary)

        subscribeUi()
        setupSearchBar()

        return binding.root
    }

    override fun onRefresh() {
        Handler().postDelayed(Runnable {
            if(!lastQuery.equals("")){
                viewModel.fetchAlbums(lastQuery)
            }
            else{
                Toast.makeText(mActivity, "Please search for an album!", Toast.LENGTH_SHORT).show()
            }
        }, 0)
    }

    override fun subscribeUi() {
        viewModel.albums.observe(this, Observer{
                albums -> updateList(albums)
        })
        viewModel.selectedAlbum.observe(this, Observer{
                album ->
            //only try to navigate if album exists and if the state of the controller is not already in details
            if(album != null && mActivity.navHostFragment.navController.currentDestination?.id == R.id.album_list_fragment){
                val direction = AlbumsListFragmentDirections.actionListFragmentToDetailsFragment(viewModel.selectedAlbumCoverUrl)
                mActivity.navHostFragment.navController.navigate(direction)
            }
        })
        viewModel.loadingVisibility.observe(this, Observer{
                loadingVisibility -> setListLoading(loadingVisibility == View.VISIBLE)
        })
    }

    fun updateList(albums: ArrayList<Album>?){
        if(albums != null){
            adapter.updateList(albums)
            if(albums.size < 1){
                binding.rvAlbums.visibility = View.GONE
                binding.tvNoItems.visibility = View.VISIBLE
            }
            else{
                binding.rvAlbums.visibility = View.VISIBLE
                binding.tvNoItems.visibility = View.GONE
            }
        }
    }

    fun setListLoading(loading: Boolean){
        enableUI(!loading)
        binding.swipeContainer.isRefreshing = loading
    }

    fun setupSearchBar(){
        binding.searchToolbar.searchBar.setPlaceHolder("")
        binding.searchToolbar.searchBar.setHint("Search Albums...")
        binding.searchToolbar.searchBar.setSearchIcon(R.drawable.ic_search)
        binding.searchToolbar.searchBar.setNavButtonEnabled(false)
        binding.searchToolbar.searchBar.setOnSearchActionListener(this)
        binding.searchToolbar.frameOverlap.setOnTouchListener { v, event -> true }
    }

    override fun onButtonClicked(buttonCode: Int) {
        when(buttonCode){
            BUTTON_BACK -> {
                binding.searchToolbar.searchBar.text = ""
                binding.searchToolbar.searchBar.disableSearch()
            }
            BUTTON_SPEECH ->  binding.searchToolbar.searchBar.enableSearch()
        }
    }

    override fun onSearchStateChanged(enabled: Boolean) {
        if(enabled){
            binding.searchToolbar.frameOverlap.visibility = View.VISIBLE
        }
        else{
            binding.searchToolbar.frameOverlap.visibility = View.GONE
        }
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        lastQuery = text.toString()
        viewModel.fetchAlbums(text.toString())
        binding.searchToolbar.searchBar.disableSearch()
    }

}