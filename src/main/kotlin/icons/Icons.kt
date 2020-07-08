package icons

import com.intellij.openapi.util.IconLoader
import javax.swing.Icon

object Icons {

    @JvmField
    val Clock: Icon = IconLoader.getIcon("/icons/clock.svg")

    @JvmField
    val Close: Icon = IconLoader.getIcon("/icons/close.png")

    @JvmField
    val ClosePressed: Icon = IconLoader.getIcon("/icons/closePressed.png")
}