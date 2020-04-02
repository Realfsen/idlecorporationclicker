package com.example.idlecorporationclicker.model

/* Creates the Building interface */
abstract class Building {
    abstract val type: BuildingType
    abstract val bName: String
    abstract var bValue: Int
    abstract var level: Int
    abstract var upgradeCost: Int

    fun upgrade() {
        /* level++ */
    }

    abstract fun makeBuilding()
}