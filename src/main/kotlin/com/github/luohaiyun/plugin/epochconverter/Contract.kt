package com.github.luohaiyun.plugin.epochconverter

import com.intellij.openapi.Disposable


interface View : Disposable {

    val disposed: Boolean
}