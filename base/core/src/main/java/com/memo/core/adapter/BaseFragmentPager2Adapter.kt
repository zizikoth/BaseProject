package com.memo.core.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * title:ViewPager2配合Fragment适配器
 * describe:
 *
 * @author memo
 * @date 2022-12-07 14:36
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class BaseFragmentPager2Adapter : FragmentStateAdapter {

    constructor(activity: FragmentActivity) : super(activity)

    constructor(fragment: Fragment) : super(fragment)

    private val fragments: ArrayList<Fragment> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(fragments: List<Fragment>): BaseFragmentPager2Adapter {
        this.fragments.clear()
        this.fragments.addAll(fragments)
        this.notifyDataSetChanged()
        return this
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}