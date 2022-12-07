package com.memo.business.widget

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import com.memo.business.databinding.LayoutSerachBarBinding
import com.memo.core.utils.ext.onClick
import com.memo.core.utils.ext.value

/**
 * title:搜索框
 * describe:
 *
 * @author memo
 * @date 2022-12-09 18:16
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
class SearchBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null) : FrameLayout(context, attrs) {

    private var mBinding: LayoutSerachBarBinding

    init {
        mBinding = LayoutSerachBarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun getValue() = mBinding.mEtSearch.value

    fun setOnSearchListener(action: (String) -> Unit) {
        mBinding.mEtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                action.invoke(mBinding.mEtSearch.value)
            }
            false
        }
        mBinding.mIvSearch.onClick {
            action.invoke(mBinding.mEtSearch.value)
        }
    }

}