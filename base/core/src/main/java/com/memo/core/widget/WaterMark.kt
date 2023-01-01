package com.memo.core.widget

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.drawable.Drawable
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.memo.core.R
import com.memo.core.utils.ext.color
import com.memo.core.utils.ext.sp2px
import kotlin.math.sqrt

/**
 * title:添加水印
 * describe:
 *
 * @author memo
 * @date 2022-03-21 10:50
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object WaterMark {

    fun add(activity: AppCompatActivity, text: String) {
        val contentView = activity.findViewById<FrameLayout>(android.R.id.content)
        if (contentView.findViewById<FrameLayout>(R.id.waterMark) == null) {
            val waterMarkView = FrameLayout(activity).apply {
                layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
                background = WaterMarkDrawable(text)
                id = R.id.waterMark
            }
            contentView.addView(waterMarkView)
        }
    }

    fun remove(activity: AppCompatActivity) {
        val contentView = activity.findViewById<FrameLayout>(android.R.id.content)
        val waterMarkView = contentView.findViewById<FrameLayout>(R.id.waterMark)
        if (waterMarkView != null) {
            contentView.removeView(waterMarkView)
        }
    }
}

class WaterMarkDrawable(private val waterMark: String) : Drawable() {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = color(R.color.color_BBBBBB)
        textSize = 12.sp2px.toFloat()
    }

    override fun draw(canvas: Canvas) {
        val width = bounds.right
        val height = bounds.bottom
        val diagonal = sqrt(width * width.toFloat() + height * height)
        val textWidth = mPaint.measureText(waterMark)
        canvas.drawColor(0x00000000)
        canvas.rotate(-25f)
        var index = 0
        var positionX: Float
        var positionY = diagonal / 10
        while (positionY <= diagonal) {
            positionX = -width + (index++ % 2) * textWidth
            while (positionX <= width) {
                canvas.drawText(waterMark, positionX, positionY, mPaint)
                positionX += textWidth * 2
            }
            positionY += diagonal / 10
        }
        canvas.save()
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {}

    override fun setColorFilter(colorFilter: ColorFilter?) {}

    @Deprecated("Deprecated in Java", ReplaceWith("PixelFormat.TRANSLUCENT", "android.graphics.PixelFormat"))
    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

}
