package com.example.idlecorporationclicker.buildings

import com.example.idlecorporationclicker.model.Building
import com.example.idlecorporationclicker.model.BuildingType

class AttackBuilding(
    override val type: BuildingType,
    override val bName: String,
    override var bValue: Int,
    override var level: Int,
    override var upgradeCost: Int
) : Building() {
    override fun makeBuilding() {

    }

}