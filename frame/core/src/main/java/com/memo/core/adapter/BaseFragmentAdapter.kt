package com.memo.core.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
class BaseFragmentAdapter(activity: AppCompatActivity, private val fragments: ArrayList<Fragment>) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = fragments.size
    override fun createFragment(position: Int): Fragment = fragments[position]
}