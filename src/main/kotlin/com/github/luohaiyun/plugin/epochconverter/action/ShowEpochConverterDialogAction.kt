package com.github.luohaiyun.plugin.epochconverter.action

import com.github.luohaiyun.plugin.epochconverter.util.EpochConverterUIManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAware

class ShowEpochConverterDialogAction : AnAction(), DumbAware {

    init{
        isEnabledInModalContext = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        if(!ApplicationManager.getApplication().isHeadlessEnvironment){
            EpochConverterUIManager.showDialog(e.project)
        }
    }
}