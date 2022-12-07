package com.memo.business.utils.indicator

import androidx.viewpager2.widget.ViewPager2
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2021-02-01 12:13 AM
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
fun MagicIndicator.init(vp2: ViewPager2, indicator: CommonNavigator) {
    if (indicator is ElasticLineIndicator) {
        indicator.setOnTabClickListener { vp2.currentItem = it }
    } else if(indicator is RoundCoverIndicator) {
        indicator.setOnTabClickListener { vp2.currentItem = it }
    }
    vp2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            indicator.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            indicator.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            indicator.onPageSelected(position)
        }

    })
    this.navigator = indicator
}