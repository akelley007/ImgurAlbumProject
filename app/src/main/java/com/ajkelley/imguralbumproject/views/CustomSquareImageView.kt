package com.ajkelley.imguralbumproject.views

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView


//custom square imageview class for more uniform gallery grid items
class CustomSquareImageView: AppCompatImageView {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
    }

    //Squares the thumbnail
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec, widthMeasureSpec)
    }
}