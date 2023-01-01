package com.memo.base.entity.remote

data class UserInfo(
    val id: Int = 0,
    val collectIds: ArrayList<Int> = arrayListOf(),
    val nickname: String = ""
)