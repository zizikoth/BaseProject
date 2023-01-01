package com.memo.mine.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.memo.base.entity.remote.RankRecord
import com.memo.mine.R

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-12-14 20:11
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class RankAdapter : BaseQuickAdapter<RankRecord, BaseViewHolder>(R.layout.layout_item_rank) {

    override fun convert(holder: BaseViewHolder, item: RankRecord) {
        holder.setGone(R.id.mIvRank, item.rank > 3)
            .setGone(R.id.mTvRank, item.rank <= 3)
            .setImageResource(
                R.id.mIvRank,
                if (item.rank == 1) R.drawable.icon_first else if (item.rank == 2) R.drawable.icon_second else R.drawable.icon_third)
            .setText(R.id.mTvRank, item.rank.toString())
            .setText(R.id.mTvName, item.username)
            .setText(R.id.mTvLevel, "Lv." + item.level)
            .setText(R.id.mTvCoin, item.coinCount.toString())
    }
}