package com.github.luohaiyun.plugin.epochconverter.ui

import com.github.luohaiyun.plugin.epochconverter.View
import com.github.luohaiyun.plugin.epochconverter.message
import com.github.luohaiyun.plugin.epochconverter.ui.form.EpochConverterDialogForm
import com.github.luohaiyun.plugin.epochconverter.util.Dates
import com.github.luohaiyun.plugin.epochconverter.util.SelectionMode
import com.github.luohaiyun.plugin.epochconverter.util.alphaBlend
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Disposer
import com.intellij.ui.*
import com.intellij.ui.components.panels.NonOpaquePanel
import com.intellij.util.concurrency.AppExecutorUtil
import com.intellij.util.ui.JBDimension
import com.intellij.util.ui.JBEmptyBorder
import com.intellij.util.ui.JBFont
import icons.Icons
import java.awt.*
import java.awt.event.*
import java.util.*
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import javax.swing.JComponent
import javax.swing.border.LineBorder

class EpochConverterDialog(private val project: Project?) : EpochConverterDialogForm(project), View {


    private val closeButton = ActionLink(icon = Icons.Close, hoveringIcon = Icons.ClosePressed) { close() }

    private var _disposed: Boolean = false
    override val disposed: Boolean get() = _disposed

    private lateinit var windowListener: WindowListener
    private lateinit var activityListener: AWTEventListener
    private lateinit var scheduledFuture: ScheduledFuture<*>

    init {
        isModal = false
        setUndecorated(true)
        peer.setContentPane(createCenterPanel())

        setResizable()
        initUIComponents()
        initScheduled()
        setListeners()
    }

    override fun createCenterPanel(): JComponent = component.apply {
        preferredSize = JBDimension(WIDTH, HEIGHT)
        border = BORDER
    }

    private fun setResizable() {
        val resizableListener = ResizableListener()
        component.apply {
            addMouseMotionListener(resizableListener)
            addMouseListener(resizableListener)
        }
    }

    private fun initScheduled() {
        scheduledFuture = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay({
            nowField.text = (System.currentTimeMillis() / 1000).toString()
        }, 0, 1, TimeUnit.SECONDS)

    }

    private fun initUIComponents() {
        rootPane.andTransparent()

        initTitle()
        initPanel()
        initReConvertComboBox()
        initReadOnlyTextField()
        initLabel()
        initConvertTextField()
        initReConvertTextField()
        initFont()

        convert()
        reConvert()

        convertButton.apply {
            addActionListener { convert() }
        }
        reConvertButton.apply {
            addActionListener {
                reConvert()
            }
        }
    }

    private fun initPanel(){
        component.apply {
            background =  UI.getColor("ToolWindow.Header.background", JBColor(0xEEF1F3, 0x353739))
                    ?.alphaBlend(CONTENT_BACKGROUND, 0.6f)
            val borderColor = UI.getBordersColor(JBColor(0xB1B1B1, 0x282828))
            border = SideBorder(borderColor, SideBorder.BOTTOM)
        }
    }

    private fun initFont(){

        UI.primaryFont(17).asBold().let {
            nowField.font = it
        }

        UI.primaryFont(10).asBold().let {
            convertGMTLabel.font = it
            convertLocalLabel.font = it
            convertRelativeLabel.font = it
            epochTimestampLabel.font = it
            convertAssumingLabel.font = it
        }

    }

    private fun initReadOnlyTextField() {
        nowField.border = null
        convertGMTTextField.border = null
        convertLocalTextField.border = null
        convertRelativeTextField.border = null
        reConvertLocalTextField.border = null
        reConvertEpochTimestampTextField.border = null
        reConvertGMTTextField.border = null
    }

    private fun initConvertTextField() {
        convertField.text = (System.currentTimeMillis() / 1000).toString()
    }

    private fun initReConvertTextField() {
        val calendar = Calendar.getInstance()
        yearField.text = calendar.get(Calendar.YEAR).toString()
        monField.text = (calendar.get(Calendar.MONTH) + 1).toString()
        dayField.text = calendar.get(Calendar.DAY_OF_MONTH).toString()
        hourField.text = calendar.get(Calendar.HOUR_OF_DAY).toString()
        minField.text = calendar.get(Calendar.MINUTE).toString()
        secField.text = calendar.get(Calendar.SECOND).toString()
    }

    private fun initLabel() {

    }


    private fun initReConvertComboBox() = with(reConvertComboBox) {
        model = CollectionComboBoxModel(listOf(SelectionMode.GMT, SelectionMode.LOCAL))
        renderer = SimpleListCellRenderer.create { label, value, _ ->
            when (value) {
                SelectionMode.GMT -> {
                    label.text = message("dialog.selection.gmt")
                }
                SelectionMode.LOCAL -> {
                    label.text = message("dialog.selection.local")
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
            setText(message("dialog.label.title"))
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

    private fun reConvert() {

        reConvertResultPanel.isVisible = true

        val selectionMode = reConvertComboBox.selectedItem as SelectionMode

        val calendar = if (selectionMode == SelectionMode.GMT) {
            Calendar.getInstance(TimeZone.getTimeZone(SelectionMode.GMT.name));
        } else {
            Calendar.getInstance()
        }

        calendar.set(yearField.text.toInt(), monField.text.toInt() - 1, dayField.text.toInt(), hourField.text.toInt(), minField.text.toInt(), secField.text.toInt())

        val timestamp = calendar.time.time

        reConvertGMTTextField.text = Dates.timestampToGMTDateStr(timestamp)
        reConvertLocalTextField.text = Dates.timestampToLocalDateStr(timestamp)
        reConvertEpochTimestampTextField.text = (timestamp / 1000) .toString()
    }


    private fun convert() {
        val timestamp = convertField.text.toLong()
        convertResultPanel.isVisible = true
        val timeUnit = Dates.getTimeUnit(timestamp)
        if (timeUnit == TimeUnit.SECONDS) {
            convertAssumingLabel.text = message("dialog.label.seconds")
        } else {
            convertAssumingLabel.text = message("dialog.label.milliseconds")
        }
        convertGMTTextField.text = Dates.timestampToGMTDateStr(timestamp)
        convertLocalTextField.text = Dates.timestampToLocalDateStr(timestamp)
        convertRelativeTextField.text = Dates.getFromNow(timestamp)
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
                closeButton.isVisible = true
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
        scheduledFuture.cancel(false)


        Disposer.dispose(this)
    }


    private inner class ResizableListener : MouseAdapter() {

        private var resizeFlag = 0
        private var startX = 0
        private var startY = 0
        private val startLocation = Point()
        private val startSize = Dimension()

        private val MouseEvent.resizeFlag: Int
            get() {
                var flag = 0
                val component = source as JComponent
                if (x in 0..RESIZE_TOUCH_SIZE) {
                    flag = flag or RESIZE_FLAG_LEFT
                }
                if (x >= component.width - RESIZE_TOUCH_SIZE && x <= component.width) {
                    flag = flag or RESIZE_FLAG_RIGHT
                }
                if (y >= component.height - RESIZE_TOUCH_SIZE && y <= component.height) {
                    flag = flag or RESIZE_FLAG_BOTTOM
                }

                return flag
            }

        private fun JComponent.updateCursor(flag: Int) {
            val cursor = when {
                flag == RESIZE_FLAG_LEFT -> Cursor.W_RESIZE_CURSOR
                flag == RESIZE_FLAG_RIGHT -> Cursor.E_RESIZE_CURSOR
                flag == RESIZE_FLAG_BOTTOM -> Cursor.S_RESIZE_CURSOR
                flag and RESIZE_FLAG_LEFT != 0 && flag and RESIZE_FLAG_BOTTOM != 0 -> Cursor.SW_RESIZE_CURSOR
                flag and RESIZE_FLAG_RIGHT != 0 && flag and RESIZE_FLAG_BOTTOM != 0 -> Cursor.SE_RESIZE_CURSOR
                else -> Cursor.DEFAULT_CURSOR
            }

            this.cursor = Cursor.getPredefinedCursor(cursor)
        }

        override fun mouseMoved(e: MouseEvent) {
            if (resizeFlag == 0) {
                (e.source as JComponent).updateCursor(e.resizeFlag)
            }
        }

        override fun mousePressed(e: MouseEvent) {
            resizeFlag = if (e.button == MouseEvent.BUTTON1) e.resizeFlag else 0
            if (resizeFlag != 0) {
                startX = e.xOnScreen
                startY = e.yOnScreen
                startLocation.location = window.location
                startSize.size = window.size
            }

            (e.source as JComponent).updateCursor(resizeFlag)
        }

        override fun mouseReleased(e: MouseEvent) {
            if (e.button == MouseEvent.BUTTON1) {
                resizeFlag = 0
                (e.source as JComponent).cursor = Cursor.getDefaultCursor()
            }
        }

        override fun mouseDragged(e: MouseEvent) {
            if (resizeFlag == 0) {
                return
            }

            var x = startLocation.x
            var w = startSize.width
            var h = startSize.height

            val dx = e.xOnScreen - startX
            val dy = e.yOnScreen - startY

            if (resizeFlag and RESIZE_FLAG_LEFT != 0) {
                w = maxOf(WIDTH, w - dx)
                x = x - w + startSize.width
            } else if (resizeFlag and RESIZE_FLAG_RIGHT != 0) {
                w = maxOf(WIDTH, w + dx)
            }
            if (resizeFlag and RESIZE_FLAG_BOTTOM != 0) {
                h = maxOf(HEIGHT, h + dy)
            }

            window.setBounds(x, startLocation.y, w, h)
            window.revalidate()
        }
    }


    companion object {
        private const val WIDTH = 200
        private const val HEIGHT = 400

        private const val RESIZE_TOUCH_SIZE = 3
        private const val RESIZE_FLAG_LEFT = 0b001
        private const val RESIZE_FLAG_RIGHT = 0b010
        private const val RESIZE_FLAG_BOTTOM = 0b100

        private val CONTENT_BACKGROUND
            get() = JBColor(Color.WHITE, UI.getColor("Editor.background", Color(0x2B2B2B))!!)
        private val DEFAULT_BORDER_COLOR = JBColor(0x808080, 0x232323)
        private val BORDER get() = LineBorder(UI.getBordersColor(DEFAULT_BORDER_COLOR))
    }

}