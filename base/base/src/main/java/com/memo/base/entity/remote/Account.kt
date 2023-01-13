package com.memo.base.entity.remote

/**
 * 用户信息
 * @property id Int 用户id
 * @property collectIds ArrayList<Int> 用户收藏文章id
 * @property nickname String 昵称
 * @constructor
 */
data class UserInfo(
    val id: Int = 0,
    val collectIds: ArrayList<Int> = arrayListOf(),
    val nickname: String = ""
)