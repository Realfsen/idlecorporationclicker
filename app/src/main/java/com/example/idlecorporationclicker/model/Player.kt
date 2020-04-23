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
    var money : Int
    var buildingFactory : BuildingFactory

    init {
        name = "Kent"
        lastSynched = Date()
        var factory = FactoryProvider()
        buildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory

        passiveIncomeBuildings = mutableListOf(buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME))
        attackBuildings= mutableListOf(buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK))
        defenseBuildings = mutableListOf(buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE))
        money = 0
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
       money += moneyPerSecond().toInt()
    }

    fun addMoneySinceLastSynched() {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastSynched.getTime()
        lastSynched = synchedNow
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        money += difinSec.times(moneyPerSecond()).toInt()
    }

    fun upgradeBuilding(building: IBuilding) : Boolean {
        if(building.calculateUpgradeCost() < money) {
            money - building.calculateUpgradeCost()
            building.upgrade()
            return true
        }
        return false
    }
}