package ink.ptms.adyeshach.impl.entity.type

import ink.ptms.adyeshach.core.entity.EntityTypes
import ink.ptms.adyeshach.core.entity.type.AdyDisplay
import ink.ptms.adyeshach.core.entity.type.AdySheep
import ink.ptms.adyeshach.core.entity.type.AdyTextDisplay
import ink.ptms.adyeshach.core.util.getEnum
import ink.ptms.adyeshach.impl.util.ifTrue
import org.bukkit.DyeColor
import org.bukkit.entity.Display
import org.bukkit.entity.TextDisplay
import taboolib.common.platform.function.info
import taboolib.common5.cbool
import taboolib.common5.cint
import taboolib.module.chat.ComponentText
import taboolib.module.chat.Components
import kotlin.experimental.and

/**
 * Adyeshach
 * ink.ptms.adyeshach.impl.entity.type.DefaultDisplay
 *
 * @author 坏黑
 * @since 2023/1/10 00:25
 */
@Suppress("SpellCheckingInspection")
abstract class DefaultDisplay(entityTypes: EntityTypes) : DefaultEntity(entityTypes), AdyDisplay {

    override fun setCustomMeta(key: String, value: String?): Boolean {
        super.setCustomMeta(key, value).ifTrue { return true }
        return when (key) {
            "brightnessoverride", "brightness_override" -> {
                if (value != null) {
                    val args = value.split(',')
                    setBrightnessOverride(Display.Brightness(args.getOrNull(0).cint, args.getOrNull(1).cint))
                } else {
                    setBrightnessOverride(null)
                }
                true
            }
            else -> false
        }
    }
}