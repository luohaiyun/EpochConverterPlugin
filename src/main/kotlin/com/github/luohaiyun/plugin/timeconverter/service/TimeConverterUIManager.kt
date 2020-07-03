package com.github.luohaiyun.plugin.timeconverter.service

import com.github.luohaiyun.plugin.timeconverter.ui.TimeConverterDialog
import com.github.luohaiyun.plugin.timeconverter.util.Application
import com.github.luohaiyun.plugin.timeconverter.util.checkDispatchThread
import com.github.luohaiyun.plugin.timeconverter.util.d
import com.intellij.ide.AppLifecycleListener
import com.intellij.openapi.Disposable
import com.intellij.openapi.components.ServiceManager
import com.intellij.openapi.diagnostic.Logger

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.util.Disposer

class TimeConverterUIManager private constructor() {

    private val dialogMap: MutableMap<Project?, TimeConverterDialog> = HashMap()

    init {
        Application.messageBus.connect().subscribe(AppLifecycleListener.TOPIC, object : AppLifecycleListener {
            override fun appClosing() = disposeUI()
        })
    }

    /**
     * 显示对话框
     *
     * @return 对话框实例
     */
    fun showDialog(project: Project?): TimeConverterDialog {
        return showDialog(
                project,
                dialogMap
        ) { TimeConverterDialog(project) }
    }


    /**
     * 关闭显示中的气泡和对话框
     */
    fun disposeUI(project: Project? = null) {
        checkThread()

        if (project == null) {
            for ((_, dialog) in dialogMap) {
                dialog.close()
            }
        } else {
            dialogMap[project]?.close()
        }

        LOGGER.d("Dispose project's UI: project=$project")
    }

    companion object {
        private val LOGGER: Logger = Logger.getInstance(TimeConverterUIManager::class.java)

        val instance: TimeConverterUIManager
            get() = ServiceManager.getService(TimeConverterUIManager::class.java)

        private fun checkThread() = checkDispatchThread(TimeConverterUIManager::class.java)

        private inline fun <D : DialogWrapper> showDialog(
                project: Project?,
                cache: MutableMap<Project?, D>,
                onBeforeShow: (D) -> Unit = {},
                dialog: () -> D
        ): D {
            checkThread()
            return cache.getOrPut(project) {
                dialog().also {
                    cache[project] = it
                    Disposer.register(it.disposable, Disposable {
                        checkThread()
                        cache.remove(project, it)
                    })
                }
            }.also {
                onBeforeShow(it)
                it.show()
            }
        }
    }

}