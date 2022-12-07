package com.memo.chapter.ui.adapter

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.memo.business.entity.remote.Chapter
import com.memo.chapter.ui.fragment.ChapterItemFragment

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
class ChapterFragmentAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    private val chapters:ArrayList<Chapter> = arrayListOf()

    fun getData() = chapters

    fun setChapters(data: List<Chapter>) {
        this.chapters.clear()
        this.chapters.addAll(data)
    }

    override fun getItemCount(): Int = chapters.size

    override fun createFragment(position: Int): Fragment = ChapterItemFragment.newInstance(chapters[position].id.toInt())

}