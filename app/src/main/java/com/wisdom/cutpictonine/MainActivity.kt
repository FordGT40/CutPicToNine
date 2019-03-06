package com.wisdom.cutpictonine

import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wisdom.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_title_bar.*

class MainActivity : BaseActivity() {
    override fun setlayoutIds() {
        setContentView(R.layout.activity_main)
    }

    override fun initViews() {
        head_back_iv.visibility = View.GONE
        setTitle(R.string.main_title)
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(this).load(R.drawable.eg).apply(options).into(iv_inside)
    }


}
