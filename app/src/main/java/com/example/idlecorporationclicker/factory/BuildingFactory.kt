package com.example.idlecorporationclicker.factory

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.buildings.DefenseBuilding
import com.example.idlecorporationclicker.buildings.IncomeBuilding
import com.example.idlecorporationclicker.model.BuildingType

class BuildingFactory() : AbstractFactory {

    var level : Int
    init {
       level = 1
    }

    public fun setBuildingLevel(level : Int) {
       this.level = level
    }


    override fun <T, BUILDING_TYPE> create(b : BUILDING_TYPE) : T {
        when(b) {
            BuildingType.ATTACK -> return AttackBuilding(this.level) as T
            BuildingType.INCOME-> return IncomeBuilding(this.level) as T
            BuildingType.DEFENSE-> return DefenseBuilding(this.level) as T
        }
        return null as T
    }

}