package com.memo.core.utils.ext

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.module.AppGlideModule
import com.memo.core.R
import com.memo.core.core.CoreApp
import java.io.InputStream

@GlideModule
class GlideAppModule : AppGlideModule()

fun ImageView.load(url: Any) {
    GlideApp.with(this).load(url)
        .placeholder(R.drawable.image_holder).error(R.drawable.image_holder)
        .centerCrop().into(this)
}

fun ImageView.load(url: Any, holderRes: Int) {
    GlideApp.with(this).load(url)
        .placeholder(holderRes).error(holderRes)
        .centerCrop()
        .into(this)
}

fun ImageView.loadRound(url: Any, radius: Int) {
    GlideApp.with(this).load(url)
        .placeholder(R.drawable.image_holder).error(R.drawable.image_holder)
        .transform(CenterCrop(), RoundedCorners(radius))
        .into(this)
}

fun ImageView.loadCircle(url: Any) {
    GlideApp.with(this).load(url)
        .placeholder(R.drawable.image_holder).error(R.drawable.image_holder)
        .transform(CenterCrop(), CircleCrop())
        .into(this)
}

fun clearGlideDiskCache(owner: LifecycleOwner) {
    owner.doInBackground {
        GlideApp.get(CoreApp.app.applicationContext).clearDiskCache()
    }
}

fun clearGlideMemoryCache() {
    GlideApp.get(CoreApp.app.applicationContext).clearMemory()
}
