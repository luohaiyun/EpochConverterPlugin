package com.github.luohaiyun.plugin.epochconverter

import com.intellij.AbstractBundle
import org.jetbrains.annotations.PropertyKey


const val BUNDLE = "messages.EpochConverterBundle"

object EpochConverterBundle : AbstractBundle(BUNDLE)

fun message(@PropertyKey(resourceBundle = BUNDLE) key: String, vararg params: Any): String {
    return EpochConverterBundle.getMessage(key, *params)
}