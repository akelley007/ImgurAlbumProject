package com.ajkelley.imguralbumproject.dependancyInjection

import com.ajkelley.imguralbumproject.fragments.BaseFragment
import com.ajkelley.imguralbumproject.viewModels.AlbumsListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(ApiModule::class)])
interface ApiComponent {
    fun inject(viewModel: AlbumsListViewModel)
    fun inject(fragment: BaseFragment)


    @Component.Builder
    interface Builder {
        fun build(): ApiComponent
        fun apiModule(apiModule: ApiModule): Builder
    }
}