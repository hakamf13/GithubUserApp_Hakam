package com.dicoding.submissions.githubuserapp_hakam.ext

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.loadImage(avatarUrl: String?) {
    Glide.with(this.context).load(avatarUrl).apply(
        RequestOptions().override(200, 200)
    ).centerCrop().circleCrop().into(this)
}

fun ImageView.setImageDrawableExt(@DrawableRes drawable: Int) {
    setImageDrawable(ContextCompat.getDrawable(this.context, drawable))
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}