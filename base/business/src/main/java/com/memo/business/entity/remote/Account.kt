data class UserInfo(
    val admin: Boolean = false,
    val coinCount: Int = 0,
    val collectIds: List<Int> = arrayListOf(),
    val email: String = "",
    val icon: String = "",
    val id: Int = 0,
    val nickname: String = "",
    val password: String = "",
    val publicName: String = "",
    val token: String = "",
    val type: Int = 0,
    val username: String = ""
)