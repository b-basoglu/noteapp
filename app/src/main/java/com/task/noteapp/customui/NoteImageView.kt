package com.task.noteapp.customui

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.squareup.picasso.Picasso
import com.task.noteapp.R

class NoteImageView : AppCompatImageView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setImage(imageUrl: String?) {
        if(!TextUtils.isEmpty(imageUrl)){
            Picasso.get().load(imageUrl).fit().centerCrop()
                .placeholder(R.drawable.ic_place_holder)
                .error(R.drawable.ic_error_image)
                .into(this)
        }
    }
}