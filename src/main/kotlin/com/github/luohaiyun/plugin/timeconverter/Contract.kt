package com.github.luohaiyun.plugin.timeconverter

import com.intellij.openapi.Disposable


interface View : Disposable {

    val disposed: Boolean
}