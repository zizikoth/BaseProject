package com.memo.base.api

import com.blankj.utilcode.util.TimeUtils
import com.memo.base.entity.local.*
import com.memo.base.entity.remote.*
import com.memo.base.manager.DataManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import rxhttp.wrapper.param.RxHttp
import rxhttp.wrapper.param.toFlowResponse

/**
 * title:网络请求数据仓库
 * describe:
 *
 * @author memo
 * @date 2023-01-01 15:35
 * @email zhou_android@163.com
 *
 * Talk is cheap, Show me the code.
 */
object ApiRepository {

    /**
     * 登录
     * @param username String   用户名
     * @param password String   密码
     * @return Flow<UserInfo>
     */
    fun login(username: String, password: String): Flow<UserInfo> {
        return RxHttp.postForm("user/login").add("username", username).add("password", password).toFlowResponse()
    }

    /**
     * 注册
     * @param username String   用户名
     * @param password String   密码
     * @return Flow<Any>
     */
    fun register(username: String, password: String): Flow<Any> {
        return RxHttp.postForm("user/register").add("username", username).add("password", password).toFlowResponse()
    }

    /**
     * 退出登录
     * @return Flow<Any?>
     */
    fun loginOut(): Flow<Any?> {
        return RxHttp.get("/user/loginout/json").toFlowResponse()
    }

    /**
     * 首页轮播图
     * @return Flow<ArrayList<Article>>
     */
    fun getBanner(): Flow<ArrayList<Article>> {
        return RxHttp.get("/banner/json").toFlowResponse()
    }

    /**
     * 首页公众号 | 公众号列表
     * @return Flow<ArrayList<Chapter>>
     */
    fun getChapters(): Flow<ArrayList<Chapter>> {
        return RxHttp.get("/wxarticle/chapters/json").toFlowResponse()
    }

    /**
     * 首页置顶文章
     * @return Flow<ArrayList<Article>>
     */
    fun getTopArticles(): Flow<ArrayList<Article>> {
        return RxHttp.get("/article/top/json").toFlowResponse()
    }

    /**
     * 首页最新项目
     * @return Flow<ListEntity<Article>>
     */
    fun getNewProject(): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/listproject/0/json").toFlowResponse()
    }

    /**
     * 首页文章
     * @param pageNum Int   页码
     * @return Flow<ListEntity<Article>>
     */
    fun getArticles(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/list/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 搜索热词
     * @return Flow<ArrayList<HotKey>>
     */
    fun getHotKey(): Flow<ArrayList<HotKey>> {
        return RxHttp.get("/hotkey/json").toFlowResponse()
    }

    /**
     * 关键字搜索文章
     * @param keyword String    关键字
     * @param pageNum Int       页码
     * @return Flow<ListEntity<Article>>
     */
    fun searchArticle(keyword: String, pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.postForm("/article/query/%d/json", pageNum).add("k", keyword).add("page_size", 40).toFlowResponse()
    }

    /**
     * 获取公众号下的文章
     * @param cid Int           公众号id
     * @param pageNum Int       页码
     * @param keyword String    搜索关键字
     * @return Flow<ListEntity<Article>>
     */
    fun getChapterArticle(cid: Int, pageNum: Int, keyword: String): Flow<ListEntity<Article>> {
        return RxHttp.get("/wxarticle/list/%d/%d/json", cid, pageNum).add("page_size", 40).add("k", keyword).toFlowResponse()
    }

    /**
     * 获取最新项目
     * @param pageNum Int   页码
     * @return Flow<ListEntity<Article>>
     */
    fun getNewProject(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/listproject/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 获取项目类型
     * @return Flow<ArrayList<Chapter>>
     */
    fun getProjects(): Flow<ArrayList<Chapter>> {
        return RxHttp.get("/project/tree/json").toFlowResponse()
    }

    /**
     * 获取项目类型文章
     * @param cid Int       项目类型id
     * @param pageNum Int   页码
     * @return Flow<ListEntity<Article>>
     */
    fun getProjectArticles(cid: Int, pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/project/list/%d/json", pageNum).add("page_size", 40).add("cid", cid).toFlowResponse()
    }

    /**
     * 获取系统分类
     * @return Flow<ArrayList<Category>>
     */
    fun getSystem(): Flow<ArrayList<Category>> {
        return RxHttp.get("/tree/json").toFlowResponse()
    }

    /**
     * 查询系统分类下的文章
     * @param pageNum Int   页码
     * @param cid Int       系统分类id
     * @return Flow<ListEntity<Article>>
     */
    fun getSystemArticles(pageNum: Int, cid: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/article/list/%d/json", pageNum).add("cid", cid).add("page_size", 40).toFlowResponse()
    }

    /**
     * 获取导航分类网址
     * @return Flow<ArrayList<Navi>>
     */
    fun getNaviWebsite(): Flow<ArrayList<Navi>> {
        return RxHttp.get("/navi/json").toFlowResponse()
    }


    /**
     * 获取所有收藏文章列表
     * @param pageNum Int   页码
     * @return Flow<ListEntity<Article>>
     */
    fun getArticleCollectList(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/lg/collect/list/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 添加站内文章收藏
     * @param id Int
     * @return Flow<Any?>
     */
    fun addInnerArticleCollect(id:Int): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/%d/json", id).toFlowResponse()
    }

    /**
     * 取消收藏站内文章
     * @param id Int
     * @return Flow<Any?>
     */
    fun deleteCollectInDetail(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect_originId/%d/json", id).toFlowResponse()
    }

    /**
     * 取消收藏 在收藏列表
     * @param collectId Int 收藏id
     * @param originId Int  原始文章id 收藏的外部文章为-1
     * @return Flow<Any?>
     */
    fun deleteCollectInCollect(collectId: Int, originId: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/uncollect/%d/json", collectId).add("originId", originId).toFlowResponse()
    }

    /**
     * 收藏站外文章
     * @param title String  标题
     * @param author String 作者
     * @param link String   地址
     * @return Flow<Article>
     */
    fun addOuterArticleCollect(title: String, author: String, link: String): Flow<Article> {
        return RxHttp.postForm("lg/collect/add/json").add("title", title).add("author", author).add("link", link).toFlowResponse()
    }

    /**
     * 修改收藏的站外文章
     * @param id Int        收藏id
     * @param title String  标题
     * @param author String 作者
     * @param link String   地址
     * @return Flow<Article>
     */
    fun editOuterArticleCollect(id: Int, title: String, author: String, link: String): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/user_article/update/%d/json", id).add("title", title).add("author", author).add("link", link)
            .toFlowResponse()
    }

    /**
     * 获取用户积分信息
     * @return Flow<CoinInfo>
     */
    fun getCoinInfo(): Flow<CoinInfo> {
        return RxHttp.get("/lg/coin/userinfo/json").toFlowResponse<CoinInfo>()
    }

    /**
     * 获取用户积分记录
     * @param pageNum Int   页码
     * @return Flow<ListEntity<CoinRecord>>
     */
    fun getCoinList(pageNum: Int): Flow<ListEntity<CoinRecord>> {
        return RxHttp.get("/lg/coin/list/%d/json", pageNum).toFlowResponse()
    }

    /**
     * 获取应用排名数据
     * @param pageNum Int   页码
     * @return Flow<ListEntity<RankInfo>>
     */
    fun getRankList(pageNum: Int): Flow<ListEntity<RankRecord>> {
        return RxHttp.get("/coin/rank/%d/json", pageNum).toFlowResponse()
    }

    /**
     * 分享广场文章
     * @param pageNum Int   页码
     * @return Flow<ListEntity<Article>>
     */
    fun getSquareShareArticles(pageNum: Int): Flow<ListEntity<Article>> {
        return RxHttp.get("/user_article/list/%d/json", pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 获取用户分享文章
     * @param userId Int    用户id
     * @param pageNum Int   页码
     * @return Flow<UserShareRecord>
     */
    fun getUserShareArticles(userId: Int, pageNum: Int): Flow<UserShareRecord> {
        return RxHttp.get("/user/%d/share_articles/%d/json", userId, pageNum).add("page_size", 40).toFlowResponse()
    }

    /**
     * 获取收藏网址列表
     * @return Flow<ArrayList<WebSite>>
     */
    fun getWebSiteCollectList(): Flow<ArrayList<WebUrl>> {
        return RxHttp.get("/lg/collect/usertools/json").toFlowResponse()
    }

    /**
     * 添加收藏网址
     * @param name String   标题
     * @param link String   地址
     * @return Flow<WebSite>
     */
    fun addWebSiteCollect(name: String, link: String): Flow<WebUrl> {
        return RxHttp.postForm("/lg/collect/addtool/json").add("name", name).add("link", link).toFlowResponse()
    }

    /**
     * 修改收藏网址
     * @param id Int        收藏id
     * @param name String   标题
     * @param link String   地址
     * @return Flow<WebSite>
     */
    fun editWebSiteCollect(id: Int, name: String, link: String): Flow<WebUrl> {
        return RxHttp.postForm("/lg/collect/updatetool/json").add("id", id).add("name", name).add("link", link).toFlowResponse()
    }

    /**
     * 取消收藏网址
     * @param id Int    收藏id
     * @return Flow<Any?>
     */
    fun deleteWebSiteCollect(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/collect/deletetool/json").add("id", id).toFlowResponse()
    }

    /**
     * 获取我的未读消息
     * @return Flow<Int>
     */
    fun unreadMessageCount(): Flow<Int> {
        return RxHttp.get("/message/lg/count_unread/json").toFlowResponse()
    }

    /**
     * 我的未读消息列表
     * @param pageNum Int   页码
     * @return Flow<ListEntity<NotifyMessage>>
     */
    fun unreadMessageList(pageNum: Int): Flow<ListEntity<NotifyMessage>> {
        return RxHttp.get("/message/lg/unread_list/%d/json", pageNum).toFlowResponse()
    }

    /**
     * 我的已读消息列表
     * @param pageNum Int   页码
     * @return Flow<ListEntity<NotifyMessage>>
     */
    fun readedMessageList(pageNum: Int): Flow<ListEntity<NotifyMessage>> {
        return RxHttp.get("/message/lg/readed_list/%d/json", pageNum).toFlowResponse()
    }

    /**
     * 获取todo列表
     * @param filter TodoFilter 筛选条件
     * @return Flow<ListEntity<TodoInfo>>
     */
    fun todoList(filter: TodoFilter): Flow<ListEntity<TodoInfo>> {
        val request = RxHttp.get("/lg/todo/v2/list/%d/json", filter.pageNum)
        if (filter.priority != TodoPriority.ALL) request.add("priority", filter.priority.value)
        if (filter.status != TodoStatus.ALL) request.add("status", filter.status.value)
        if (filter.type != TodoType.ALL) request.add("type", filter.type.value)
        request.add("orderby", filter.orderBy.value)
        return request.toFlowResponse()
    }

    /**
     * 新增Todo
     * @param params TodoContent 输入内容
     * @return Flow<TodoInfo>
     */
    fun todoAdd(params: TodoContent): Flow<TodoInfo> {
        return RxHttp.postForm("/lg/todo/add/json").add("title", params.title).add("content", params.content)
            .add("date", TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd"))).add("type", params.type)
            .add("priority", params.priority).toFlowResponse()
    }

    /**
     * 修改Todo
     * @param params TodoContent 输入内容
     * @return Flow<TodoInfo>
     */
    fun todoEdit(params: TodoContent): Flow<TodoInfo> {
        return RxHttp.postForm("/lg/todo/update/%d/json", params.id).add("title", params.title).add("content", params.content)
            .add("date", TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyy-MM-dd"))).add("type", params.type)
            .add("priority", params.priority).toFlowResponse()
    }

    /**
     * 删除Todo
     * @param id Int    Todo的ID
     * @return Flow<Any?>
     */
    fun todoDelete(id: Int): Flow<Any?> {
        return RxHttp.postForm("/lg/todo/delete/%d/json", id).toFlowResponse()
    }

    /**
     * 修改Todo状态
     * @param id Int                id
     * @param status TodoStatus    状态
     * @return Flow<Any?>
     */
    fun todoStatus(id: Int, status: TodoStatus): Flow<Any?> {
        return RxHttp.postForm("/lg/todo/done/%d/json", id).add("status", status.value).toFlowResponse()
    }

}