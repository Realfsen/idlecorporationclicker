package com.example.idlecorporationclicker.model

/* Creates the Building interface */
interface IBuilding {
    val type: BuildingType
    val name: String
    var value: Int
    var level: Int
    var upgradeCost: Int
    fun upgrade()
    fun calculateValue() : Int
    fun calculateUpgradeCost() : Int
}

enum class BuildingType {
    ATTACK, DEFENSE, INCOME
}
