package com.github.luohaiyun.plugin.epochconverter.ui

import com.intellij.openapi.Disposable
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.VerticalFlowLayout
import com.intellij.ui.components.panels.NonOpaquePanel
import com.intellij.util.ui.JBUI
import javax.swing.JComponent

abstract class EpochConvertPane<T : JComponent>(
        private val project: Project?
) : NonOpaquePanel(VerticalFlowLayout(JBUI.scale(GAP), JBUI.scale(GAP))), Disposable {





    companion object {
        const val GAP = 5
    }

}