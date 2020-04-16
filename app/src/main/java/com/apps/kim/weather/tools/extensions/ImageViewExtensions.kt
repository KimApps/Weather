package com.apps.kim.weather.tools.extensions

import androidx.annotation.DrawableRes
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * Created by Volodymyr Kim`
 * vkim.uae@gmail.com
 */
fun ImageView.loadImage(imageUri: String, @DrawableRes placeholder: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().centerCrop().placeholder(placeholder).error(placeholder))
        .into(this)
}

fun ImageView.loadImageWithError(imageUri: String, @DrawableRes placeholder: Int, @DrawableRes error: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().centerCrop().placeholder(placeholder).error(error))
        .into(this)
}

fun ImageView.loadImageAsBitmap(imageUri: String, @DrawableRes placeholder: Int) {
    Glide.with(this.context)
        .asBitmap()
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().centerCrop().placeholder(placeholder).error(placeholder))
        .into(this)
}

fun ImageView.loadImage(imageUri: String) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().centerCrop())
        .into(this)
}

fun ImageView.loadImage(resId: Int) {
    Glide.with(this.context)
        .load(resId)
        .into(this)
}

fun ImageView.loadImageWithoutCrop(imageUri: String) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .into(this)
}

fun ImageView.loadCircularImage(imageUri: String, @DrawableRes placeholder: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().placeholder(placeholder).error(placeholder).circleCrop())
        .into(this)
}

fun ImageView.loadRoundedTop(imageUri: String, @DrawableRes placeholder: Int, radius: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().centerCrop().placeholder(placeholder).error(placeholder))
        .apply(
            bitmapTransform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(
                        radius,
                        0,
                        RoundedCornersTransformation.CornerType.TOP
                    )
                )
            )
        )
        .into(this)

}

fun ImageView.loadRoundedImage(imageUri: String, @DrawableRes placeholder: Int, radius: Int) {
    Glide.with(this.context)
        .load(imageUri)
        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
        .apply(RequestOptions().centerCrop().placeholder(placeholder).error(placeholder))
        .apply(
            bitmapTransform(
                MultiTransformation(
                    CenterCrop(),
                    RoundedCornersTransformation(
                        radius,
                        0,
                        RoundedCornersTransformation.CornerType.ALL
                    )
                )
            )
        )
        .into(this)
}