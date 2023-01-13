package com.memo.base.entity.local

/**
 * Todo的类型
 */
enum class TodoType(val value: Int, val desc: String) {
    ALL(-1, "全部"),
    WORK(1, "工作"),
    LIFE(2, "生活"),
    MINE(3, "个人"),
    OTHER(4, "其他")
}

/**
 * Todo的状态
 */
enum class TodoStatus(val value: Int, val desc: String) {
    ALL(-1, "全部"),
    UN_COMPLETE(0, "未完成"),
    COMPLETE(1, "已完成")
}

/**
 * Todo的优先级
 */
enum class TodoPriority(val value: Int, val desc: String) {
    ALL(-1, "全部"),
    IMMEDIATE(4, "立刻马上"),
    HIGH(3, "比较重要"),
    NORMAL(2, "不重要的"),
    LOW(1, "无所谓的")
}

/**
 * Todo的排序
 */
enum class TodoOrderBy(val value: Int, val desc: String) {
    CREATE_DATE_DESC(4, "创建时间逆序"),
    CREATE_DATE_ASC(3, "创建时间顺序"),
    COMPLETE_DATE_DESC(2, "完成时间逆序"),
    COMPLETE_DATE_ASC(1, "完成时间顺序")
}

/**
 * Todo的筛选条件
 */
data class TodoFilter(
    var pageNum: Int = 1,
    var type: TodoType = TodoType.ALL,
    var status: TodoStatus = TodoStatus.ALL,
    var priority: TodoPriority = TodoPriority.ALL,
    var orderBy: TodoOrderBy = TodoOrderBy.CREATE_DATE_DESC)


/**
 * Todo新增修改内容
 */
data class TodoContent(
    var id: Int? = null,
    var title: String = "",
    var content: String = "",
    var priority: Int = -1,
    var type: Int = -1)