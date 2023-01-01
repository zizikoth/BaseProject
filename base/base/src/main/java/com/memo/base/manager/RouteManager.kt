package com.memo.base.manager

import androidx.fragment.app.Fragment
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

    const val ProjectFragment = "/Project/ProjectFragment"
    const val SystemFragment = "/System/SystemFragment"
    const val MineFragment = "/Mine/MineFragment"

    const val AccountActivity =  "/Main/AccountActivity"
    const val BlogActivity = "/Blog/BlogActivity"
    const val NewProjectActivity = "/Project/NewProjectActivity"
    const val SearchActivity = "/Search/SearchActivity"

    /**
     * 获取ProjectFragment
     */
    fun getProjectFragment(): Fragment {
        return ARouter.getInstance().build(ProjectFragment).navigation() as Fragment
    }

    /**
     * 获取SystemFragment
     */
    fun getSystemFragment(): Fragment {
        return ARouter.getInstance().build(SystemFragment).navigation() as Fragment
    }

    /**
     * 获取MineFragment
     * @return Fragment
     */
    fun getMineFragment():Fragment{
        return ARouter.getInstance().build(MineFragment).navigation() as Fragment
    }

    /**
     * 跳转账号界面
     */
    fun startAccountActivity(){
        ARouter.getInstance().build(AccountActivity).navigation()
    }

    /**
     * 跳转搜索页面
     */
    fun startSearchActivity() {
        ARouter.getInstance().build(SearchActivity).navigation()
    }

    /**
     * 跳转公众号界面
     * @param cid Int 公众号Id
     */
    fun startBlogActivity(cid: Int) {
        ARouter.getInstance().build(BlogActivity).withInt("cid", cid).navigation()
    }

    /**
     * 跳转项目界面
     */
    fun startNewProjectActivity() {
        ARouter.getInstance().build(NewProjectActivity).navigation()
    }

}