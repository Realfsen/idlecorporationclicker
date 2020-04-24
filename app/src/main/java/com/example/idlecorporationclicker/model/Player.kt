package com.example.idlecorporationclicker.model

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.buildings.DefenseBuilding
import com.example.idlecorporationclicker.buildings.IncomeBuilding
import com.example.idlecorporationclicker.factory.BuildingFactory
import com.example.idlecorporationclicker.factory.FACTORY_TYPE
import com.example.idlecorporationclicker.factory.FactoryProvider
import com.example.idlecorporationclicker.database.DatabaseController
import java.util.*
import java.util.concurrent.TimeUnit

class Player {
    var name : String
    var lastSynched : Date
//    var attackBuildings : MutableList<IBuilding>
//    var defenseBuildings : MutableList<IBuilding>
//    var passiveIncomeBuildings: MutableList<IBuilding>
    var lastAttack : Date
    var attackBuilding : IBuilding
    var defenseBuilding :IBuilding
    var passiveIncomeBuilding: IBuilding
    var money : Long = 0
    set(value) {
        field = value
        DatabaseController.playerUpdateMoney()
    }
    var buildingFactory : BuildingFactory

    init {
        name = "Kent"
        lastSynched = Date()
        lastAttack = Date()
        var factory = FactoryProvider()
        buildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory
//
//        passiveIncomeBuildings = mutableListOf(buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME))
//        attackBuildings= mutableListOf(buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK))
//        defenseBuildings = mutableListOf(buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE))

        passiveIncomeBuilding = buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME)
        attackBuilding = buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK)
        defenseBuilding = buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE)
        money = 0
    }

    fun moneyPerSecond() : Double {
//        var monPrSec : Double = 0.0
//        passiveIncomeBuildings.forEach() {
//            monPrSec += it.value
//        }

//        return monPrSec
        return passiveIncomeBuilding.value
    }

    fun canAttack() : Boolean {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastAttack.getTime()
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        return difinSec > IAttack.companion.SECONDS_BETWEEN_ATTACKS
    }

    fun secondsTillAttack() : Int {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastAttack.getTime()
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        return IAttack.companion.SECONDS_BETWEEN_ATTACKS-difinSec.toInt()
    }

    fun defense() : Double {
//        var defense : Double = 0.0
//        defenseBuildings.forEach() {
//            defense += it.value
//        }
//        return defense
        return defenseBuilding.value
    }

    fun attack() : Double {
//        var attack: Double = 0.0
//        attackBuildings.forEach() {
//            attack += it.value
//        }
//        return attack
        return attackBuilding.value
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
        DatabaseController.SyncMoneyWithFirestoreController()
    }

    fun buyBuilding(building: IBuilding) : Boolean {
        if(building.calculateUpgradeCost() < money) {
            money - building.calculateUpgradeCost()
            building.upgrade()
            return true
        }
        return false
    }

    fun sellBuilding(building: IBuilding) {
        if(building.level > 1) {
            money = money + building.sellValue().toInt()
            building.downgrade()
        }
    }


    fun hasMoneyForBuilding(building: IBuilding) : Boolean {
       return building.calculateUpgradeCost() < money
    }

}