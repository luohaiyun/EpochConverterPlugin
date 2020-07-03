package com.github.luohaiyun.plugin.timeconverter.ui

import com.github.luohaiyun.plugin.timeconverter.View
import com.github.luohaiyun.plugin.timeconverter.message
import com.github.luohaiyun.plugin.timeconverter.ui.form.TimeConverterDialogForm
import com.github.luohaiyun.plugin.timeconverter.util.Dates
import com.github.luohaiyun.plugin.timeconverter.util.SelectionMode
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.*
import com.intellij.ui.awt.RelativePoint
import com.intellij.ui.components.panels.NonOpaquePanel
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import icons.Icons
import java.awt.AWTEvent
import java.awt.Color
import java.awt.Toolkit
import java.awt.event.*
import javax.swing.JComponent
import javax.swing.border.LineBorder

class TimeConverterDialog(private val project: Project?) : TimeConverterDialogForm(project), View {


    private val closeButton = ActionLink(icon = Icons.Close, hoveringIcon = Icons.ClosePressed) { close() }

    private var _disposed: Boolean = false
    override val disposed: Boolean get() = _disposed

    private lateinit var windowListener: WindowListener
    private lateinit var activityListener: AWTEventListener

    init {
        isModal = false
        setUndecorated(true)
        peer.setContentPane(createCenterPanel())

        setResizable(true)
        initUIComponents()
        setListeners()
    }

    override fun createCenterPanel(): JComponent = component.apply {
        preferredSize = JBDimension(WIDTH, HEIGHT)
        border = BORDER
    }

    private fun initUIComponents() {
        rootPane.andTransparent()

        initTitle()
        initConvertComboBox()
        initReConvertComboBox()

        convertButton.apply {
            addActionListener { convertInternal(convertField.text) }
        }
    }

    private fun initConvertComboBox() = with(convertComboBox) {
        model = CollectionComboBoxModel(listOf(SelectionMode.GMT, SelectionMode.UTC, SelectionMode.CST))
        renderer = SimpleListCellRenderer.create { label, value, _ ->
            when(value){
                SelectionMode.GMT -> {
                    label.text = "GMT"
                    label.toolTipText = message("dialog.tooltip.GMT")
                }
                SelectionMode.UTC -> {
                    label.text = "UTC"
                    label.toolTipText = message("dialog.tooltip.UTC")
                }
            }
        }
    }

    private fun initReConvertComboBox() = with(reConvertComboBox){
        model = CollectionComboBoxModel(listOf(SelectionMode.GMT, SelectionMode.UTC, SelectionMode.CST))
        renderer = SimpleListCellRenderer.create { label, value, _ ->
            when(value){
                SelectionMode.GMT -> {
                    label.text = "GMT"
                    label.toolTipText = message("dialog.tooltip.GMT")
                }
                SelectionMode.UTC -> {
                    label.text = "UTC"
                    label.toolTipText = message("dialog.tooltip.UTC")
                }
            }
        }
    }



    private fun initTitle() {
        closeButton.apply {
            isVisible = false
            disabledIcon = Icons.ClosePressed
        }
        titlePanel.apply {
            setText("Convert")
            setActive(true)

            setButtonComponent(object : ActiveComponent {
                override fun getComponent(): JComponent = NonOpaquePanel().apply {
                    preferredSize = closeButton.preferredSize
                    add(closeButton)
                }

                override fun setActive(active: Boolean) {
                    closeButton.isEnabled = active
                }
            }, JBEmptyBorder(0, 0, 0, 2))

            WindowMoveListener(this).let {
                addMouseListener(it)
                addMouseMotionListener(it)
            }
        }
    }



    private fun convertInternal(text: String) {
        val timestamp = text.toLong()
        val converted = Dates.timestampToLocalDateStr(timestamp)

        convertTextArea.text = converted
    }



    private fun setListeners() {
        windowListener = object : WindowAdapter() {
            override fun windowActivated(e: WindowEvent) {
                titlePanel.setActive(true)
            }

            override fun windowDeactivated(e: WindowEvent) {
                titlePanel.setActive(false)
            }

            override fun windowClosed(e: WindowEvent) {
                // 在对话框上打开此对话框时，关闭主对话框时导致此对话框也跟着关闭，
                // 但资源没有释放干净，回调也没回完整，再次打开的话就会崩溃
                close()
            }
        }
        window.addWindowListener(windowListener)

        activityListener = AWTEventListener {
            if (it is MouseEvent && it.id == MouseEvent.MOUSE_MOVED) {
//                val inside = isInside(RelativePoint(it))
//                if (inside != lastMoveWasInsideDialog) {
//                    closeButton.isVisible = inside
//                    lastMoveWasInsideDialog = inside
//                }
            }
        }
        Toolkit.getDefaultToolkit().addAWTEventListener(activityListener, AWTEvent.MOUSE_MOTION_EVENT_MASK)
    }


    fun close() {
        close(CLOSE_EXIT_CODE)
    }

    override fun dispose() {
        if (disposed) {
            return
        }

        super.dispose()
        _disposed = true

        window.removeWindowListener(windowListener)
        Toolkit.getDefaultToolkit().removeAWTEventListener(activityListener)

        Disposer.dispose(this)
        println("Dialog disposed.")
    }


    companion object {
        private const val WIDTH = 400
        private const val HEIGHT = 500

        private const val RESIZE_TOUCH_SIZE = 3
        private const val RESIZE_FLAG_LEFT = 0b001
        private const val RESIZE_FLAG_RIGHT = 0b010
        private const val RESIZE_FLAG_BOTTOM = 0b100

        private val CONTENT_BACKGROUND
            get() = JBColor(Color.WHITE, UI.getColor("Editor.background", Color(0x2B2B2B))!!)
        private val DEFAULT_BORDER_COLOR = JBColor(0x808080, 0x232323)
        private val BORDER get() = LineBorder(UI.getBordersColor(DEFAULT_BORDER_COLOR))

        private const val CARD_MASSAGE = "message"
        private const val CARD_PROCESSING = "processing"
        private const val CARD_TRANSLATION = "translation"
    }

}