package com.memo.base.entity.local

enum class TodoType(val value: Int, val desc: String) {
    WORK(1, "工作"), LIFE(2, "生活"), MINE(3, "个人"), OTHER(4, "其他"), ALL(-1, "全部")
}

enum class TodoStatus(val value: Int, val desc: String) {
    UN_COMPLETE(0, "未完成"), COMPLETE(1, "已完成"), ALL(-1, "全部")
}

enum class TodoPriority(val value: Int, val desc: String) {
    IMMEDIATE(4, "立刻马上"), HIGH(3, "比较重要"), NORMAL(2, "一般般吧"), LOW(1, "不重要啦"), ALL(-1, "全部")
}

enum class TodoOrderBy(val value: Int, val desc: String) {
    COMPLETE_DATE_UP(1, "完成时间顺序"), COMPLETE_DATE_DOWN(2, "完成时间逆序"), CREATE_DATE_UP(3, "创建时间顺序"), CREATE_DATE_DOWN(4, "创建时间逆序")
}

data class TodoFilter(
    var pageNum: Int = 1,
    var type: TodoType = TodoType.ALL,
    var status: TodoStatus = TodoStatus.ALL,
    var priority: TodoPriority = TodoPriority.ALL,
    var orderBy: TodoOrderBy = TodoOrderBy.CREATE_DATE_UP)


data class TodoContent(
    var id: Int = 0,
    var title: String = "",
    var content: String = "",
    var date: String = "",
    var priority: Int = -1,
    var status: Int = -1,
    var type: Int = -1)