package com.example.idlecorporationclicker.buildings

import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding

class AttackBuilding(
    override val type: BuildingType,
    override val name: String,
    override var value: Int,
    override var level: Int,
    override var upgradeCost: Int
) : IBuilding {
    override fun upgrade() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}