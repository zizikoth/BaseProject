package com.memo.base.utils.web

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.Utils
import com.just.agentweb.AgentWeb
import com.just.agentweb.AgentWebConfig

/**
 * title:网页工具
 * describe:
 *
 * @author zhou
 * @date 2019-08-08 14:10
 */
object WebHelper {

    /**
     * 初始化网页
     * @param activity AppCompatActivity    当前页面
     * @param container ViewGroup           网页容器
     * @param errorLayoutRes Int            网页加载失败的站位图
     * @param url String?                   网页地址
     * @return AgentWeb
     */
    @SuppressLint("SetJavaScriptEnabled", "ObsoleteSdkInt")
    fun init(activity: AppCompatActivity, container: ViewGroup, @LayoutRes errorLayoutRes: Int, url: String?): AgentWeb {

        //如果是空白网址 设置一个错误的地址
        val httpUrl = url ?: ""
        val agentWeb = AgentWeb.with(activity).setAgentWebParent(container, FrameLayout.LayoutParams(-1, -1)).useDefaultIndicator()
            .setWebViewClient(BlockWebClient()).setMainFrameErrorView(errorLayoutRes, -1).createAgentWeb().ready().go(httpUrl)

        val webView = agentWeb.webCreator.webView
        val settings = webView.settings
        //去除过度拉伸效果
        webView.overScrollMode = WebView.OVER_SCROLL_NEVER
        //允许缓存
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.domStorageEnabled = true
        //适应屏幕
        settings.loadWithOverviewMode = true
        settings.useWideViewPort = true
        //允许js交互
        settings.javaScriptEnabled = true
        //启动双指放大
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        //先网页后图片
        settings.blockNetworkImage = false

        //适配5.0不允许http和https混合使用情况
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }

        // 生命周期配置
        activity.lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                agentWeb.webLifeCycle.onResume()
            }

            override fun onPause(owner: LifecycleOwner) {
                agentWeb.webLifeCycle.onPause()
            }

            override fun onDestroy(owner: LifecycleOwner) {
                agentWeb.webLifeCycle.onDestroy()
            }
        })

        return agentWeb
    }

    /**
     * 清除本地缓存
     */
    fun clearCache() {
        AgentWebConfig.clearDiskCache(Utils.getApp())
    }

    /**
     * android调用网页js方法
     * @param method 方法名
     * @param callback 方法回调
     * @param params 传参
     */
    fun callJs(agentWeb: AgentWeb, method: String, callback: ValueCallback<String>? = null, vararg params: String) {
        agentWeb.jsAccessEntrace.quickCallJs(method, callback, *params)
    }

    /**
     * android响应网页js方法
     * 例如：网页调方法 window.android.showToast(message)
     * className = android
     * method类 必须有showToast(message:String)方法 并且方法添加@JavascriptInterface注解
     *
     * @param className 方法名
     * @param methodClazz Any
     *
     */
    fun respondJs(agentWeb: AgentWeb, className: String, methodClazz: Any) {
        agentWeb.jsInterfaceHolder.addJavaObject(className, methodClazz)
    }

    /**
     * 判断网页是否可以返回
     * @return Boolean
     */
    fun onBackPress(agentWeb: AgentWeb): Boolean = agentWeb.back()

}