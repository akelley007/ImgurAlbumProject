package com.ajkelley.imguralbumproject.extensions

import android.content.ContextWrapper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders


fun View.getParentActivity(): AppCompatActivity?{
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun Fragment.getViewModelProvider() =
    activity?.let(ViewModelProviders::of) ?: ViewModelProviders.of(this)

inline fun <reified T : ViewModel> Fragment.getViewModel() = getViewModelProvider().get(T::class.java)