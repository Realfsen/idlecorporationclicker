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
    var attackBuildings : MutableList<IBuilding>
    var defenseBuildings : MutableList<IBuilding>
    var passiveIncomeBuildings: MutableList<IBuilding>
    var buildingFactory : BuildingFactory
    var income : Int

    init {
        name = "Kent"
        lastSynched = Date()
        var factory = FactoryProvider()
        buildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory

        passiveIncomeBuildings = mutableListOf(buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME))
        attackBuildings= mutableListOf(buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK))
        defenseBuildings = mutableListOf(buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE))
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
        defenseBuildings.forEach() {
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


    fun buyBuilding(type: BuildingType, building: IBuilding) {
        when(type) {
            BuildingType.ATTACK-> building.upgrade()
            BuildingType.DEFENSE-> building.upgrade()
            BuildingType.INCOME-> building.upgrade()
        }
    }

}