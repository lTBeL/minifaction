package com.sjj.fiction.model

import com.sjj.fiction.util.DATE_PATTERN_DEF
import com.sjj.fiction.util.formatDate
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.*

@ConfigurationProperties(prefix = "auth")
@PropertySource("file:auth.properties")
@Component
class Auth {
    var ignoreVersions = mutableSetOf<String>()
    val agree = mutableSetOf<String>()
    val reject = mutableSetOf<String>()
    val active = mutableMapOf<String, String>()
    fun record(id: String) {
        active[id] = DATE_PATTERN_DEF.formatDate(Date())
    }

    @Synchronized
    fun saveAuthToDisk() {
        var stream: OutputStream? = null;
        try {
            stream = FileOutputStream(File("auth.properties"))
            val properties = Properties();
            agree.forEachIndexed { index, s ->
                properties.setProperty("auth.agree[$index]", s)
            }
            reject.forEachIndexed { index, s ->
                properties.setProperty("auth.reject[$index]", s)
            }
            ignoreVersions.forEachIndexed { index, s ->
                properties.setProperty("auth.ignoreVersions[$index]", s)
            }
            properties.store(stream, "auth list")
        } finally {
            stream?.close()
        }
    }

}