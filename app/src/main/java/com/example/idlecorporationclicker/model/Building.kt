package com.example.idlecorporationclicker.model

/* Creates the Building interface */
interface Building {
    val type: BuildingType
    val name: String
    val value: Int
    val level: Int
    val upgradeCost: Int

    fun upgrade()
}

enum class BuildingType {
    ATTACK, DEFENSE, INCOME
}