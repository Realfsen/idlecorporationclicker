package com.example.idlecorporationclicker.model

import com.badlogic.gdx.scenes.scene2d.ui.Image

/* Creates the Building interface */
interface IBuilding {
    abstract val type: BuildingType
    abstract val name: String
    abstract var value: Double
    abstract var level: Double
    abstract var upgradeCost: Double
    abstract val image : Image
    abstract fun upgrade()
    abstract fun calculateValue() : Double
    abstract fun calculateUpgradeCost() : Double
}

enum class BuildingType {
    NONE, ATTACK, DEFENSE, INCOME
}
