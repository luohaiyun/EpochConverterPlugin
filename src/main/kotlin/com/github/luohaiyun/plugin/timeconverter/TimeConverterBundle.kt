package com.github.luohaiyun.plugin.timeconverter

import com.intellij.AbstractBundle
import org.jetbrains.annotations.PropertyKey


const val BUNDLE = "messages.TimeConverterBundle"

object TimeConverterBundle : AbstractBundle(BUNDLE)

fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
    return TimeConverterBundle.getMessage(key, *params)
}