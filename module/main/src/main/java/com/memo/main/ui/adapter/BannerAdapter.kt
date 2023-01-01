package com.memo.main.ui.adapter

import android.widget.ImageView
import com.memo.base.entity.remote.Article
import com.memo.core.utils.ext.load
import com.memo.main.R
import com.zhpan.bannerview.BaseBannerAdapter
import com.zhpan.bannerview.BaseViewHolder

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-09 09:43
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class BannerAdapter : BaseBannerAdapter<Article>() {

    override fun bindData(holder: BaseViewHolder<Article>, data: Article, position: Int, pageSize: Int) {
        holder.findViewById<ImageView>(R.id.mIvBanner).load(data.imagePath, com.memo.core.R.color.color_F5F5F5)
    }


    override fun getLayoutId(viewType: Int): Int = R.layout.layout_item_banner
}
