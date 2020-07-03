
/*
 * Extensions
 */
@file:Suppress("unused")

package com.github.luohaiyun.plugin.timeconverter.ui

import javax.swing.JComponent

@Suppress("NOTHING_TO_INLINE")
inline fun JComponent.andTransparent() = apply { isOpaque = false }


@Suppress("NOTHING_TO_INLINE")
inline fun JComponent.andOpaque() = apply { isOpaque = true }
