package com.memo.business.entity.local

/**
 * title:数据集合
 * describe:
 *
 * @author memo
 * @date 2022-03-17 16:11
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
data class Zip2<A, B>(var first: A, var second: B)
data class Zip3<A, B, C>(var first: A, var second: B, var third: C)
data class Zip4<A, B, C, D>(var first: A, var second: B, var third: C, var forth: D)
data class Zip5<A, B, C, D, E>(var first: A, var second: B, var third: C, var forth: D, var fifth: E)

data class Zip2Null<A, B>(var first: A?, var second: B?)
data class Zip3Null<A, B, C>(var first: A?, var second: B?, var third: C?)
data class Zip4Null<A, B, C, D>(var first: A?, var second: B?, var third: C?, var forth: D?)
data class Zip5Null<A, B, C, D, E>(var first: A?, var second: B?, var third: C?, var forth: D?, var fifth: E?)