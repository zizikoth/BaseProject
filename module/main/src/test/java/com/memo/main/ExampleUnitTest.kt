package com.memo.main

import com.blankj.utilcode.util.EncryptUtils
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private val data =
        "5d0e7b81f75d1473a84d47908679277b3f4a1227669a6290720c5f1bb78efee890e494ba774ce939409eae271796600e49ab1dd65c7074fdb98f5f0e16875a37fa5b0d8cb05255f3eb05ae0a6c1c314d187eeb956935fb8d8261e8e26670b91fb0b8383c7f4434de4a912447d23b6373914ad7d13d5656647241a1314c61dbc1ca17d30d464d9d28ba6adf0996ec0a7bcc85c39d3b0de572a05aa10d1fc8380d"
    private val key = "89426153020239872480473112864968"

    @Test
    fun addition_isCorrect() {
        val json = String(EncryptUtils.decryptHexStringAES(data, key.toByteArray(), "AES", null))
        println(json)
    }
}