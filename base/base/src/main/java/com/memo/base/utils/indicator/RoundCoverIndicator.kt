package com.memo.base.utils.indicator

import android.content.Context
import android.graphics.Color
import com.memo.base.R
import com.memo.core.utils.ext.color
import com.memo.core.utils.ext.dimen
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2021-02-01 4:37 PM
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class RoundCoverIndicator(
    context: Context) :
    CommonNavigator(context) {

    private var naviHeight:Float = dimen(R.dimen.dp30)
    private var naviRadius:Float = dimen(R.dimen.dp30)
    private var naviPadding:Int = dimen(R.dimen.dp20).toInt()
    private var naviTextSize:Float = dimen(R.dimen.textNormal)
    private val naviTitles:ArrayList<String> = arrayListOf()

    private var onTabClick: (index: Int) -> Unit = {}

    override fun onAttachToMagicIndicator() {
        super.onAttachToMagicIndicator()
        adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int = naviTitles.size

            override fun getTitleView(context: Context, index: Int): IPagerTitleView {
                return ClipPagerTitleView(context).apply {
                    setPadding(naviPadding, 0, naviPadding, 0)
                    text = naviTitles[index]
                    textSize = naviTextSize
                    textColor = color(R.color.tab_normal)
                    clipColor = Color.WHITE
                    setOnClickListener { onTabClick.invoke(index) }
                }
            }

            override fun getIndicator(context: Context): IPagerIndicator {
                return LinePagerIndicator(context).apply {
                    lineHeight = naviHeight
                    roundRadius = naviRadius
                    setColors(color(R.color.tab_select))
                }
            }

        }
    }

    fun setOnTabClickListener(onTabClickListener: (index: Int) -> Unit): RoundCoverIndicator {
        this.onTabClick = onTabClickListener
        return this
    }

    fun setNaviHeight(height:Float): RoundCoverIndicator {
        this.naviHeight = height
        return this
    }

    fun setNaviRadius(radius:Float): RoundCoverIndicator {
        this.naviRadius = radius
        return this
    }

    fun setNaviPadding(padding:Int): RoundCoverIndicator {
        this.naviPadding = padding
        return this
    }

    fun setNaviTextSize(textSize:Float): RoundCoverIndicator {
        this.naviTextSize = textSize
        return this
    }

    fun setNaviTitles(titles: List<String>): RoundCoverIndicator {
        this.naviTitles.clear()
        this.naviTitles.addAll(titles)
        return this
    }

}