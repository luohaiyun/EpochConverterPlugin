package com.github.luohaiyun.plugin.epochconverter.ui

import com.intellij.util.ui.JBFont
import com.intellij.util.ui.JBUI
import java.awt.Color
import java.awt.Font
import javax.swing.UIManager

object UI {

    val defaultFont: JBFont get() = JBFont.create(Font("Microsoft YaHei", Font.PLAIN, 14))

    fun primaryFont(size: Int)
            : JBFont = getFont(null, size)


    private fun getFont(fontFamily: String?, size: Int): JBFont = if (!fontFamily.isNullOrEmpty()) {
        JBUI.Fonts.create(fontFamily, size)
    } else {
        defaultFont.deriveScaledFont(size.toFloat())
    }

    @JvmStatic
    @JvmOverloads
    fun getColor(key: String, default: Color? = null): Color? = UIManager.getColor(key) ?: default

    @JvmStatic
    @JvmOverloads
    fun getBordersColor(default: Color? = null): Color? = UIManager.getColor("Borders.color") ?: default
}