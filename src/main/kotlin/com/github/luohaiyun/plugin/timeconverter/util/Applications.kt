package com.github.luohaiyun.plugin.timeconverter.util

import com.github.luohaiyun.plugin.timeconverter.service.TimeConverterUIManager
import com.intellij.ide.IdeBundle
import com.intellij.ide.plugins.IdeaPluginDescriptor
import com.intellij.ide.plugins.PluginManager
import com.intellij.notification.Notification
import com.intellij.notification.Notifications
import com.intellij.openapi.application.Application
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.application.ModalityState
import com.intellij.openapi.application.ex.ApplicationInfoEx
import com.intellij.openapi.application.impl.ApplicationInfoImpl
import com.intellij.openapi.extensions.PluginId
import com.intellij.openapi.ide.CopyPasteManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.SystemInfo
import com.intellij.util.Alarm
import com.intellij.util.text.DateFormatUtil
import java.awt.datatransfer.StringSelection
import java.io.PrintWriter
import java.io.StringWriter
import java.text.SimpleDateFormat
import java.util.concurrent.Future


object Plugin {

    @Suppress("SpellCheckingInspection")
    const val PLUGIN_ID = "com.github.luohaiyun.plugin.timeconverter"

    val descriptor: IdeaPluginDescriptor = PluginManager.getPlugin(PluginId.getId(PLUGIN_ID))!!

    val version: String by lazy { descriptor.version }

    val systemInfo: String by lazy {
        val appInfo = ApplicationInfoEx.getInstanceEx() as ApplicationInfoImpl

        val appName = appInfo.fullApplicationName

        var buildInfo = IdeBundle.message("about.box.build.number", appInfo.build.asString())
        val cal = appInfo.buildDate
        var buildDate = ""
        if (appInfo.build.isSnapshot) {
            buildDate = SimpleDateFormat("HH:mm, ").format(cal.time)
        }
        buildDate += DateFormatUtil.formatAboutDialogDate(cal.time)
        buildInfo += IdeBundle.message("about.box.build.date", buildDate)

        val properties = System.getProperties()
        val javaVersion = properties.getProperty("java.version", "unknown")
        val javaRuntimeVersion = properties.getProperty("java.runtime.version", javaVersion)
        val arch = properties.getProperty("os.arch", "")
        val jreInfo = IdeBundle.message("about.box.jre", javaRuntimeVersion, arch)

        val vmVersion = properties.getProperty("java.vm.name", "unknown")
        val vmVendor = properties.getProperty("java.vendor", "unknown")
        val jvmInfo = IdeBundle.message("about.box.vm", vmVersion, vmVendor)

        val pluginInfo = "Plugin v$version"
        val osInfo = "OS: " + SystemInfo.getOsNameAndVersion()

        "$pluginInfo\n$appName\n$buildInfo\n$jreInfo\n$jvmInfo\n$osInfo"
    }
}


inline val Application: Application get() = ApplicationManager.getApplication()

inline val TimeConverterUIManager: TimeConverterUIManager get() = TimeConverterUIManager.instance


/**
 * Throws an [IllegalStateException] with the result of calling [lazyMessage] if
 * the current thread is not the Swing dispatch thread.
 */
inline fun checkDispatchThread(lazyMessage: () -> Any) {
    check(Application.isDispatchThread, lazyMessage)
}

/**
 * Throws an [IllegalStateException] with the [class][clazz] name if
 * the current thread is not the Swing dispatch thread.
 *
 * @see checkDispatchThread
 */
inline fun checkDispatchThread(clazz: Class<*>) = checkDispatchThread {
    "${clazz.simpleName} must only be used from the Event Dispatch Thread."
}



/**
 * Requests pooled thread to execute the [action].
 */
inline fun executeOnPooledThread(crossinline action: () -> Unit)
        : Future<*> = Application.executeOnPooledThread { action() }

/**
 * Asynchronously execute the [action] on the AWT event dispatching thread.
 */
inline fun invokeOnDispatchThread(crossinline action: () -> Unit) {
    with(Application) {
        if (isDispatchThread) {
            action()
        } else {
            invokeLater { action() }
        }
    }
}


private val alarm: Alarm = Alarm(Alarm.ThreadToUse.SWING_THREAD)

/**
 * Asynchronously execute the [action] on the AWT event dispatching thread after the given [delay][delayMillis].
 */
fun invokeLater(delayMillis: Long, modalityState: ModalityState = ModalityState.any(), action: () -> Unit) {
    alarm.addRequest(action, delayMillis, modalityState)
}

/**
 * Asynchronously execute the [action] on the AWT event dispatching thread.
 */
inline fun invokeLater(crossinline action: () -> Unit) {
    Application.invokeLater { action() }
}

/**
 * Asynchronously execute the [action] on the AWT event dispatching thread.
 *
 * @param state the state in which the runnable will be executed.
 */
inline fun invokeLater(state: ModalityState, crossinline action: () -> Unit) {
    Application.invokeLater({ action() }, state)
}

/**
 * Shows the notification[Notification].
 */
fun Notification.show(project: Project? = null) {
    Notifications.Bus.notify(this, project)
}

/**
 * Copy the stack trace to clipboard.
 */
fun Throwable.copyToClipboard(message: String? = null) {
    val stringWriter = StringWriter()
    PrintWriter(stringWriter).use {
        if (!message.isNullOrBlank()) {
            it.println(message)
            it.println()
        }

        it.println(Plugin.systemInfo.split("\n").joinToString(separator = "  \n>", prefix = ">"))
        it.println()

        it.println("```")
        printStackTrace(it)
        it.println("```")

        stringWriter.toString()
    }.let { CopyPasteManager.getInstance().setContents(StringSelection(it)) }
}
