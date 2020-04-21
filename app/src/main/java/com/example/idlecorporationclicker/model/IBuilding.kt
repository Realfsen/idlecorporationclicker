package com.example.idlecorporationclicker.model

import com.badlogic.gdx.scenes.scene2d.ui.Image

/* Creates the Building interface */
interface IBuilding {
    abstract val type: BuildingType
    abstract val name: String
    abstract var value: Int
    abstract var level: Int
    abstract var upgradeCost: Int
    abstract val image : Image
    abstract fun upgrade()
    abstract fun calculateValue() : Int
    abstract fun calculateUpgradeCost() : Int
}

enum class BuildingType {
    NONE, ATTACK, DEFENSE, INCOME
}
