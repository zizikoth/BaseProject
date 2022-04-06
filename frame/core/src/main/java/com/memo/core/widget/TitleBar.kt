package com.memo.core.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.memo.core.R
import com.memo.core.databinding.TitleBarBinding
import com.memo.core.utils.extra.dp2px
import com.memo.core.utils.extra.onClick
import com.memo.core.utils.extra.setVisible
import com.memo.core.utils.extra.visible

/**
 * title:
 * describe:
 *
 * @author memo
 * @date 2022-03-18 13:49
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class TitleBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var mBinding: TitleBarBinding

    private var titleText: String = ""
    private var subtitleText: String = ""
    private var showBack: Boolean = true
    private var rightText: String = ""
    private var rightDrawable: Int = 0

    init {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        titleText = attr.getString(R.styleable.TitleBar_title_title_text) ?: titleText
        subtitleText = attr.getString(R.styleable.TitleBar_title_subtitle_text) ?: subtitleText
        showBack = attr.getBoolean(R.styleable.TitleBar_title_show_back, showBack)
        rightText = attr.getString(R.styleable.TitleBar_title_right_text) ?: rightText
        rightDrawable = attr.getResourceId(R.styleable.TitleBar_title_right_drawable, rightDrawable)
        attr.recycle()
        mBinding = TitleBarBinding.inflate(LayoutInflater.from(context), this, true)
        mBinding.run {
            // 主标题
            mTvTitle.text = titleText
            // 副标题
            mTvSubTitle.text = subtitleText
            mTvSubTitle.setVisible(subtitleText != "")
            // 左侧
            mTvLeft.setVisible(showBack)
            mTvLeft.onClick { if (context is AppCompatActivity) context.finish() }
            // 右侧
            mTvRight.setVisible(rightText != "" || rightDrawable != 0)
            mTvRight.text = rightText
            mTvRight.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, rightDrawable, 0)
            // 底部阴影 这四行是必须的才会有阴影效果
            setBackgroundColor(Color.WHITE)
            this@TitleBar.outlineProvider = ViewOutlineProvider.BOUNDS
            ViewCompat.setElevation(this@TitleBar, 2.dp2px.toFloat())
            clipToPadding = false
        }
    }

    /**
     * 设置标题
     * @param title String? 标题文字
     * @return TitleBar
     */
    fun setTitle(title: String?): TitleBar {
        title?.let { mBinding.mTvTitle.text = it }
        return this
    }

    /**
     * 设置副标题
     * @param subTitle String? 副标题
     * @return TitleBar
     */
    fun setSubTitle(subTitle: String?): TitleBar {
        subTitle?.let { mBinding.mTvSubTitle.text = it }
        return this
    }

    /**
     * 显示返回图标
     * @param show Boolean true显示 false不显示
     * @return TitleBar
     */
    fun showBack(show: Boolean): TitleBar {
        mBinding.mTvLeft.setVisible(show)
        return this
    }

    /**
     * 设置右侧文字
     * @param rightText String 右侧文字
     * @return TitleBar
     */
    fun setRightText(rightText: String): TitleBar {
        mBinding.mTvRight.visible()
        mBinding.mTvRight.text = rightText
        return this
    }

    /**
     * 设置右侧图标
     * @param drawableRes Int 图标资源
     * @return TitleBar
     */
    fun setRightDrawable(@DrawableRes drawableRes: Int): TitleBar {
        mBinding.mTvRight.visible()
        mBinding.mTvRight.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, drawableRes, 0)
        return this
    }

    /**
     * 设置右侧点击事件
     * @param action Function0<Unit> 点击事件
     */
    fun setOnRightClickListener(action: (View) -> Unit) {
        mBinding.mTvRight.onClick { action.invoke(it) }
    }

}