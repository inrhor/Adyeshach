package ink.ptms.adyeshach.common.script.action

import ink.ptms.adyeshach.common.script.ScriptHandler.entitySelected
import ink.ptms.adyeshach.common.script.ScriptHandler.getEntities
import ink.ptms.adyeshach.common.script.ScriptHandler.getManager
import ink.ptms.adyeshach.common.script.ScriptHandler.loadError
import taboolib.module.kether.*
import java.util.concurrent.CompletableFuture

/**
 * @author IzzelAliz
 */
class ActionPassenger(val symbol: Symbol, val passenger: String?) : ScriptAction<Void>() {

    enum class Symbol {

        ADD, REMOVE, RESET
    }

    override fun run(frame: ScriptFrame): CompletableFuture<Void> {
        val script = frame.script()
        if (script.getManager() == null || !script.entitySelected()) {
            error("Manager or Entity is not selected")
        }
        script.getEntities()?.forEach {
            when (symbol) {
                Symbol.ADD -> {
                    script.getManager()!!.getEntityById(passenger!!).forEach { e -> it?.addPassenger(e) }
                }
                Symbol.REMOVE -> {
                    it!!.getPassengers().filter { e -> e.id == passenger }.forEach { e -> it.removePassenger(e) }
                }
                Symbol.RESET -> it?.clearPassengers()
            }
        }
        return CompletableFuture.completedFuture(null)
    }

    companion object {

        @KetherParser(["passenger"], namespace = "adyeshach", shared = true)
        fun parser() = scriptParser {
            val symbol = when (val type = it.nextToken()) {
                "add" -> Symbol.ADD
                "remove" -> Symbol.REMOVE
                "reset" -> Symbol.RESET
                else -> throw loadError("Unknown passenger operator $type")
            }
            ActionPassenger(symbol, if (symbol != Symbol.RESET) it.nextToken() else null)
        }
    }
}