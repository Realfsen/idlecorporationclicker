package com.example.idlecorporationclicker.model

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.buildings.DefenseBuilding
import com.example.idlecorporationclicker.buildings.IncomeBuilding

/* Defines the EntityFactory class, */
class EntityFactory(override var newB: Building) : AbstractFactory {

    override fun create(type: CreationType, bType: BuildingType) {
        if (type == CreationType.BUILDING) {
            when (bType) {
                BuildingType.ATTACK     -> newB = AttackBuilding()
                BuildingType.DEFENSE    -> newB = DefenseBuilding()
                BuildingType.INCOME     -> newB = IncomeBuilding()
            }

        }


        if (type == CreationType.ATTACK) {
            //TODO
        }
    }

}