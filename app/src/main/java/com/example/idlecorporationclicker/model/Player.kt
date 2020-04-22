package com.example.idlecorporationclicker.model

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.buildings.DefenseBuilding
import com.example.idlecorporationclicker.buildings.IncomeBuilding
import com.example.idlecorporationclicker.factory.BuildingFactory
import com.example.idlecorporationclicker.factory.FACTORY_TYPE
import com.example.idlecorporationclicker.factory.FactoryProvider
import java.util.*
import java.util.concurrent.TimeUnit

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
        for (i in 1..1) {
            buildingFactory.setBuildingLevel(i.toDouble())
            passiveIncomeBuildings.add(buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME))

        }
        attackBuildings = mutableListOf<AttackBuilding>()
        for (i in 1..2) {
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

    fun moneyPerSecond() : Double {
       var monPrSec : Double = 0.0
       passiveIncomeBuildings.forEach() {
          monPrSec += it.value
       }
        return monPrSec
    }

    fun defense() : Double {
        var defense : Double = 0.0
        defenseBuilding.forEach() {
            defense += it.value
        }
        return defense
    }
    fun attack() : Double {
        var attack: Double = 0.0
        attackBuildings.forEach() {
            attack += it.value
        }
        return attack
    }

    fun addClickMoney() {
       income += moneyPerSecond().toInt()
    }

    fun addMoneySinceLastSynched() {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastSynched.getTime()
        lastSynched = synchedNow
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        income += difinSec.times(moneyPerSecond()).toInt()
    }


    fun buyBuilding(type: BuildingType) {
        when(type) {
            BuildingType.ATTACK-> attackBuildings.add(buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK))
            BuildingType.DEFENSE-> defenseBuilding.add(buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE))
            BuildingType.INCOME-> passiveIncomeBuildings.add(buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME))
        }
    }

}