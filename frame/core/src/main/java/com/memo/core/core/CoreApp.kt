package com.memo.core.core

import android.app.Application

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-16 15:57
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
open class CoreApp : Application() {

    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}