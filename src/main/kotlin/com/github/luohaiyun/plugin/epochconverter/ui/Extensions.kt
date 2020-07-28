
/*
 * Extensions
 */
@file:Suppress("unused")

package com.github.luohaiyun.plugin.epochconverter.ui

import com.intellij.ui.scale.JBUIScale
import com.intellij.util.ui.JBFont
import java.awt.Font
import javax.swing.JComponent

@Suppress("NOTHING_TO_INLINE")
inline fun JComponent.andTransparent() = apply { isOpaque = false }


@Suppress("NOTHING_TO_INLINE")
inline fun JComponent.andOpaque() = apply { isOpaque = true }

/**
 * Creates a new [Font][JBFont] object by replicating this [Font] object
 * and applying a new style and [scaled size][size].
 */
fun Font.deriveScaledFont(style: Int, size: Float)
        : JBFont = JBFont.create(deriveFont(style, JBUIScale.scale(size)), false)

/**
 * Creates a new [Font][JBFont] object by replicating the current
 * [Font] object and applying a new [scaled size][size] to it.
 */
fun Font.deriveScaledFont(size: Float): JBFont = JBFont.create(deriveFont(JBUIScale.scale(size)), false)