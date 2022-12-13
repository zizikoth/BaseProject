package com.memo.business.utils.indicator

import android.content.Context
import android.util.TypedValue
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.memo.business.R
import com.memo.core.utils.ext.color
import com.memo.core.utils.ext.dimen
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView

/**
 * title:项目通用Indicator
 * describe:
 *
 * @author memo
 * @date 2021-01-31 11:50 PM
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class ElasticLineIndicator(context: Context) : CommonNavigator(context) {

    private val titles = arrayListOf<String>()
    private var onTabClick: (index: Int) -> Unit = {}

    override fun onAttachToMagicIndicator() {
        super.onAttachToMagicIndicator()
        adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int = titles.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return ColorFlipPagerTitleView(context).apply {
                    text = titles[index]
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, dimen(R.dimen.textNormal))
                    normalColor = color(R.color.tab_normal)
                    selectedColor = color(R.color.tab_select)
                    setOnClickListener { onTabClick.invoke(index) }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    mode = LinePagerIndicator.MODE_EXACTLY
                    lineHeight = dimen(com.memo.core.R.dimen.dp4)
                    lineWidth = dimen(com.memo.core.R.dimen.dp14)
                    roundRadius = dimen(com.memo.core.R.dimen.dp4)
                    startInterpolator = AccelerateInterpolator()
                    endInterpolator = DecelerateInterpolator(2f)
                    yOffset = dimen(com.memo.core.R.dimen.dp6)
                    setColors(color(R.color.tab_select))
                }
            }
        }
    }

    fun setOnTabClickListener(onTabClickListener: (index: Int) -> Unit): ElasticLineIndicator {
        this.onTabClick = onTabClickListener
        return this
    }

    fun setTitles(titles: List<String>): ElasticLineIndicator {
        this.titles.clear()
        this.titles.addAll(titles)
        notifyDataSetChanged()
        return this
    }


    inner class ColorFlipPagerTitleView(context: Context?) : SimplePagerTitleView(context) {
        private var mChangePercent = 0.5f
        override fun onLeave(index: Int, totalCount: Int, leavePercent: Float, leftToRight: Boolean) {
            if (leavePercent >= mChangePercent) {
                setTextColor(mNormalColor)
            } else {
                setTextColor(mSelectedColor)
            }
        }

        override fun onEnter(index: Int, totalCount: Int, enterPercent: Float, leftToRight: Boolean) {
            if (enterPercent >= mChangePercent) {
                setTextColor(mSelectedColor)
            } else {
                setTextColor(mNormalColor)
            }
        }

        override fun onSelected(index: Int, totalCount: Int) {}
        override fun onDeselected(index: Int, totalCount: Int) {}
    }

}