package com.memo.todo.ui.activity

import com.alibaba.android.arouter.facade.annotation.Route
import com.memo.business.base.BaseVmActivity
import com.memo.business.manager.RouteManager
import com.memo.todo.databinding.ActivityTodoBinding
import com.memo.todo.viewmodel.TodoViewModel

@Route(path = RouteManager.TodoActivity)
class TodoActivity : BaseVmActivity<TodoViewModel, ActivityTodoBinding>() {

    /*** 初始化数据 ***/
    override fun initData() {
    }

    /*** 初始化控件 ***/
    override fun initView() {
    }

    /*** 初始化监听 ***/
    override fun initListener() {
    }

    /*** 页面开始请求 ***/
    override fun start() {
    }

}