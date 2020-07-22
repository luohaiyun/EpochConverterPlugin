package com.github.luohaiyun.plugin.epochconverter.ui

import java.awt.Color
import javax.swing.UIManager

object UI {


    @JvmStatic
    @JvmOverloads
    fun getColor(key: String, default: Color? = null): Color? = UIManager.getColor(key) ?: default

    @JvmStatic
    @JvmOverloads
    fun getBordersColor(default: Color? = null): Color? = UIManager.getColor("Borders.color") ?: default
}