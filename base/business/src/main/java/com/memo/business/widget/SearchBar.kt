package com.memo.business.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.FrameLayout
import androidx.core.widget.addTextChangedListener
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

    var keyword: String = "请输入搜索内容"
        get() = mBinding.mEtSearch.value
        set(value) {
            mBinding.mEtSearch.value = value
            field = value
        }

    var hint: String = ""
        get() = mBinding.mEtSearch.hint.toString()
        set(value) {
            mBinding.mEtSearch.hint = value
            field = value
        }

    /**
     * 搜索文字切换监听
     * @param action Function1<String, Unit>
     */
    fun setOnTextChangeListener(action: (String) -> Unit) {
        mBinding.mEtSearch.addTextChangedListener {
            action.invoke(keyword)
        }
    }

    /**
     * 搜索监听
     * @param action Function1<String, Unit>
     */
    fun setOnSearchListener(action: (String) -> Unit) {
        mBinding.mEtSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                action.invoke(keyword)
            }
            false
        }
        mBinding.mIvSearch.onClick {
            action.invoke(keyword)
        }
    }

}