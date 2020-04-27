package com.example.idlecorporationclicker.models.building

import com.badlogic.gdx.scenes.scene2d.ui.Image
import kotlin.math.pow

/* Creates the Building interface */
interface IBuilding {
    abstract val type: BuildingType
    abstract val name: String
    abstract var value: Double
    abstract var level: Double
    abstract var upgradeCost: Double
    abstract val image : Image
    abstract var baseCost : Double
    abstract var baseValue : Double
    abstract fun upgrade()
    abstract fun calculateValue() : Double
    abstract fun calculateUpgradeCost() : Double
    abstract fun sellValue() : Double
    abstract fun downgrade()

    fun setBuildingsStartLevel(level: Double) {
        this.level = level
        value = calculateValue()
        upgradeCost = calculateUpgradeCost()
    }
}

enum class BuildingType {
    NONE, ATTACK, DEFENSE, INCOME
}

public class IBuildingUtils {
    companion object {
        public fun calculateUpgradeCost(baseCost : Double, level : Double) : Double {
            return baseCost * (1.15).pow(level/2)
        }

        public fun calculateValue(baseValue : Double, level : Double) : Double  {
            return baseValue * level
        }
    }
}
