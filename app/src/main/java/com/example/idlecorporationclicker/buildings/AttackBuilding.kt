package com.example.idlecorporationclicker.buildings

import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding

class AttackBuilding(override var level: Int)
 : IBuilding {
    override val type: BuildingType
    override val name: String
    override var value: Int = calculateValue()
    override var upgradeCost: Int = calculateUpgradeCost()

    init {
        type = BuildingType.ATTACK;
        name = "Attack Building"
    }

    override fun calculateValue() : Int {
       return 10
    }
    override fun calculateUpgradeCost() : Int {
       return 10
    }

    override fun upgrade() {
        level++
    }


}