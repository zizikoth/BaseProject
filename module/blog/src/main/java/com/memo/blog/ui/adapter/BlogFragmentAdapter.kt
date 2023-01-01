package com.memo.blog.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.memo.base.entity.remote.Chapter
import com.memo.blog.ui.fragment.BlogItemFragment

/**
 * title:公众号文章ViewPager适配器
 * describe:
 *
 * @author memo
 * @date 2022-12-09 16:29
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class BlogFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val data:ArrayList<Chapter> = arrayListOf()

    fun getData() = data

    fun setData(list: List<Chapter>) {
        this.data.clear()
        this.data.addAll(list)
    }

    override fun getItemCount(): Int = data.size

    override fun createFragment(position: Int): Fragment = BlogItemFragment.newInstance(data[position].id)

}