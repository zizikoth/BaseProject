package com.memo.business.manager

import com.alibaba.android.arouter.launcher.ARouter

/**
 * title:项目路由
 * describe:
 *
 * @author memo
 * @date 2022-12-09 14:35
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object RouteManager {
    const val ChapterActivity = "/chapter/ChapterActivity"

    /**
     * 跳转公众号界面
     * @param chapterId Int 公众号Id
     */
    fun startChapterActivity(chapterId: Int) {
        ARouter.getInstance().build(ChapterActivity).withInt("chapterId", chapterId).navigation()
    }
}