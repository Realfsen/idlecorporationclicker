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

class Player : IPlayer {

    override var name: String = ""
    override var lastSynched: Date = Date()
    var lastAttack: Date = Date()

    val factory = FactoryProvider()
    val buildingFactory : BuildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory
    override var attackBuilding: IBuilding = buildingFactory.create<IncomeBuilding, BuildingType>(BuildingType.INCOME)
    override var defenseBuilding: IBuilding  = buildingFactory.create<AttackBuilding, BuildingType>(BuildingType.ATTACK)
    override var passiveIncomeBuilding: IBuilding = buildingFactory.create<DefenseBuilding, BuildingType>(BuildingType.DEFENSE)

    override var money : Long = 0
        set(value) {
            field = value
            DatabaseController.playerUpdateMoney()
        }

    fun moneyPerSecond() : Double {
        return passiveIncomeBuilding.value
    }

    fun canAttack() : Boolean {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastAttack.getTime()
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        return difinSec > IAttack.SECONDS_BETWEEN_ATTACKS
    }

    fun secondsTillAttack() : Int {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastAttack.getTime()
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        return IAttack.SECONDS_BETWEEN_ATTACKS-difinSec.toInt()
    }

    fun attack() : Double {
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