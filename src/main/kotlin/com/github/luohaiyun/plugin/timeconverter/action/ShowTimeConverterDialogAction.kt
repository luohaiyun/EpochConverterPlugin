package com.github.luohaiyun.plugin.timeconverter.action

import com.github.luohaiyun.plugin.timeconverter.util.TimeConverterUIManager
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.DumbAware

class ShowTimeConverterDialogAction : AnAction(), DumbAware {

    init{
        isEnabledInModalContext = true
    }

    override fun actionPerformed(e: AnActionEvent) {
        if(!ApplicationManager.getApplication().isHeadlessEnvironment){
            TimeConverterUIManager.showDialog(e.project)
        }
    }
}