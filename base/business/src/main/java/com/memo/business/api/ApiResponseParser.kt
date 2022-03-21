package com.memo.business.api

import okhttp3.Response
import rxhttp.wrapper.annotation.Parser
import rxhttp.wrapper.parse.TypeParser
import rxhttp.wrapper.utils.convertTo
import java.lang.reflect.Type

/**
 * title:对于数据返回统一解析
 * describe:
 *
 * @author memo
 * @date 2022-03-17 09:12
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
@Parser(name = "Response")
open class ApiResponseParser<T> : TypeParser<T> {

    protected constructor() : super()
    constructor(type: Type) : super(type)

    override fun onParse(response: Response): T {
        val data: ApiResponse<T> = response.convertTo(ApiResponse::class, *types)
        if (!data.success()) {
            throw ApiException(data.code, data.msg)
        }
        return data.data

    }
}