package com.memo.mine.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.memo.base.entity.remote.CoinRecord
import com.memo.mine.R

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-14 19:48
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class CoinAdapter : BaseQuickAdapter<CoinRecord, BaseViewHolder>(R.layout.layout_item_coin) {
    override fun convert(holder: BaseViewHolder, item: CoinRecord) {
        holder.setText(R.id.mTvCoin, item.desc)
    }
}