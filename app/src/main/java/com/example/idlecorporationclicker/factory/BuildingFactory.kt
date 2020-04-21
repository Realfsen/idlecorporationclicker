package com.example.idlecorporationclicker.factory

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.buildings.DefenseBuilding
import com.example.idlecorporationclicker.buildings.IncomeBuilding
import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding

class BuildingFactory() : AbstractFactory {

    var level : Double
    init {
       level = 1.0
    }

    public fun setBuildingLevel(level : Double) {
       this.level = level
    }


    override fun <IBuilding, BUILDING_TYPE> create(b : BUILDING_TYPE) : IBuilding {
        when(b) {
            BuildingType.ATTACK -> return AttackBuilding(this.level) as IBuilding
            @Suppress("UNCHECKED CAST")
            BuildingType.INCOME-> return IncomeBuilding(this.level) as IBuilding
            @Suppress("UNCHECKED CAST")
            BuildingType.DEFENSE-> return DefenseBuilding(this.level) as IBuilding
        }
        @Suppress("UNCHECKED CAST")
        return null as IBuilding
    }

}