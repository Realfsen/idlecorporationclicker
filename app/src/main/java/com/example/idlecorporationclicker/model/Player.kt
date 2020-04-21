package com.example.idlecorporationclicker.model

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.buildings.DefenseBuilding
import com.example.idlecorporationclicker.buildings.IncomeBuilding
import com.example.idlecorporationclicker.factory.BuildingFactory
import com.example.idlecorporationclicker.factory.FACTORY_TYPE
import com.example.idlecorporationclicker.factory.FactoryProvider
import java.util.*

class Player {
    var name : String
    var lastSynched : Date
    var attackBuildings : MutableList<AttackBuilding>
    var defenseBuilding : MutableList<DefenseBuilding>
    var passiveIncomeBuildings: MutableList<IncomeBuilding>
    var buildingFactory : BuildingFactory
    var income : Int

    init {
        name = "Kent"
        lastSynched = Date()
        var factory = FactoryProvider()
        buildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory

        passiveIncomeBuildings = mutableListOf<IncomeBuilding>()
        for (i in 1..3) {
            buildingFactory.setBuildingLevel(i.toDouble())
            passiveIncomeBuildings.add(buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME))

        }
        attackBuildings = mutableListOf<AttackBuilding>()
        for (i in 1..3) {
            buildingFactory.setBuildingLevel(i.toDouble())
            attackBuildings.add(buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK))

        }
        defenseBuilding = mutableListOf<DefenseBuilding>()
        for (i in 1..3) {
            buildingFactory.setBuildingLevel(i.toDouble())
            defenseBuilding.add(buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE))

        }
        income = 0
    }


    fun buyBuilding(type: BuildingType) {
        when(type) {
            BuildingType.ATTACK-> attackBuildings.add(buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK))
            BuildingType.DEFENSE-> defenseBuilding.add(buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE))
            BuildingType.INCOME-> passiveIncomeBuildings.add(buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME))
        }
    }

}