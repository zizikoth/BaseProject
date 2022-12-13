package com.memo.core.utils.ext

import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.view.*
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.view.ViewCompat
import com.blankj.utilcode.util.ImageUtils
import com.memo.core.utils.ClickHelper
import kotlin.math.min

/**
 * title:View的Kotlin拓展
 * describe:
 *
 * @author memo
 * @date 2019-11-20 14:39
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */

/**
 * 设置控件的点击事件
 * @param method
 */
fun View.onClick(method: (View) -> Unit) {
    setOnClickListener {
        if (ClickHelper.isNotFastClick) {
            method.invoke(it)
        }
    }
}

/**
 * 给组件设置阴影
 * @receiver ViewGroup
 */
fun ViewGroup.addElevation(){
    // 底部阴影 这四行是必须的才会有阴影效果
    setBackgroundColor(Color.WHITE)
    this.outlineProvider = ViewOutlineProvider.BOUNDS
    ViewCompat.setElevation(this, 2.dp2px.toFloat())
    this.clipToPadding = false
}

/**
 * 设置View的宽度和高度
 * @param width 要设置的宽度
 * @param height 要设置的高度
 */
fun View.widthAndHeight(width: Int, height: Int): View {
    val params: ViewGroup.LayoutParams? = layoutParams
    if (params == null) {
        layoutParams = ViewGroup.LayoutParams(width, height)
    } else {
        params.width = width
        params.height = height
        layoutParams = params
    }
    return this
}


/**
 * 设置View的margin  默认保留原设置
 * @param leftMargin 距离左的距离
 * @param topMargin 距离上的距离
 * @param rightMargin 距离右的距离
 * @param bottomMargin 距离下的距离
 */
fun View.margin(
    leftMargin: Int = Int.MAX_VALUE,
    topMargin: Int = Int.MAX_VALUE,
    rightMargin: Int = Int.MAX_VALUE,
    bottomMargin: Int = Int.MAX_VALUE): View {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        if (leftMargin != Int.MAX_VALUE) params.leftMargin = leftMargin
        if (topMargin != Int.MAX_VALUE) params.topMargin = topMargin
        if (rightMargin != Int.MAX_VALUE) params.rightMargin = rightMargin
        if (bottomMargin != Int.MAX_VALUE) params.bottomMargin = bottomMargin
        layoutParams = params
    }
    return this
}


/**
 * 设置控件Visible
 */
fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}

/**
 * 设置控件Gone
 */
fun View.gone(): View {
    visibility = View.GONE
    return this
}

/**
 * 设置控件Invisible
 */
fun View.invisible(): View {
    visibility = View.INVISIBLE
    return this
}

/**
 * 设置是否可见
 * @param visibleOrGone true - Visible false - Gone
 */
fun View.setVisible(visibleOrGone: Boolean): View {
    visibility = if (visibleOrGone) View.VISIBLE else View.GONE
    return this
}

/**
 * 设置控件Visible
 */
fun visible(vararg views: View?) {
    for (view in views) {
        view?.visible()
    }
}

/**
 * 设置控件Invisible
 */
fun invisible(vararg views: View?) {
    for (view in views) {
        view?.invisible()
    }
}

/**
 * 设置控件Gone
 */
fun gone(vararg views: View?) {
    for (view in views) {
        view?.gone()
    }
}

/**
 * 获取View的Bitmap
 * 支持RecyclerView ScrollView 基础控件 不支持ListView了
 * 注意:使用这个方法的时候必须要在View测量完毕之后才能进行
 */
fun View.toBitmap(): Bitmap {
    return ImageUtils.view2Bitmap(this)
}

/**
 * 控件绘制监听
 */
inline fun View.onGlobalLayoutListener(crossinline callback: () -> Unit) = with(viewTreeObserver) {
    addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        override fun onGlobalLayout() {
            callback()
            viewTreeObserver.removeOnGlobalLayoutListener(this)
        }
    })
}


/**
 * 设置控件圆角
 */
fun View.round(radius: Int = 10.dp2px) {
    background = GradientDrawable().apply {
        if (this@round.background is ColorDrawable) {
            setColor((this@round.background as ColorDrawable).color)
        }
        cornerRadius = radius.toFloat()
    }
}

fun View.round(@ColorInt color: Int, radius: Int = 10.dp2px) {
    background = GradientDrawable().apply {
        setColor(color)
        cornerRadius = radius.toFloat()
    }
}

/**
 * 设置控件圆角
 * @receiver View
 */
fun View.round(color: Int = Color.WHITE, leftTop: Int, rightTop: Int, rightBottom: Int, leftBottom: Int) {
    background = GradientDrawable().apply {
        setColor(color)
        cornerRadii = floatArrayOf(
            leftTop.toFloat(),
            leftTop.toFloat(),
            rightTop.toFloat(),
            rightTop.toFloat(),
            rightBottom.toFloat(),
            rightBottom.toFloat(),
            leftBottom.toFloat(),
            leftBottom.toFloat())
    }
}

/**
 * 设置控件圆形
 */
fun View.circle(color: Int = Color.WHITE) {
    this.post {
        if (width != height) {
            val size = min(width, height)
            widthAndHeight(size, size)
        }
        background = GradientDrawable().apply {
            setColor(color)
            cornerRadius = width / 2f
        }
    }
}
