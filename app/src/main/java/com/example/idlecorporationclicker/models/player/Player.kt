package com.example.idlecorporationclicker.models.player

import com.example.idlecorporationclicker.models.building.AttackBuilding
import com.example.idlecorporationclicker.models.building.DefenseBuilding
import com.example.idlecorporationclicker.models.building.IncomeBuilding
import com.example.idlecorporationclicker.factory.BuildingFactory
import com.example.idlecorporationclicker.factory.FACTORY_TYPE
import com.example.idlecorporationclicker.factory.FactoryProvider
import com.example.idlecorporationclicker.models.database.Database
import com.example.idlecorporationclicker.models.attack.IAttack
import com.example.idlecorporationclicker.models.building.BuildingType
import com.example.idlecorporationclicker.models.building.IBuilding
import com.google.firebase.auth.FirebaseUser
import java.util.*
import java.util.concurrent.TimeUnit

class Player : IPlayer {
    override var uid: String = ""
    override var name: String = ""
    override var email: String = ""
    override var lastSynched: Date = Date()
    override var nextTimeToSync: Long = Date().time + Database.SYNC_DELAY_SECONDS
    var lastAttack: Date = Date()

    val factory = FactoryProvider()
    val buildingFactory : BuildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory
    override var attackBuilding: IBuilding = buildingFactory.create<AttackBuilding, BuildingType>(
        BuildingType.ATTACK)
    override var defenseBuilding: IBuilding = buildingFactory.create<DefenseBuilding, BuildingType>(
        BuildingType.DEFENSE)
    override var passiveIncomeBuilding: IBuilding = buildingFactory.create<IncomeBuilding, BuildingType>(
        BuildingType.INCOME)

    override var money : Long = 0
        set(value) {
            field = value
            Database.SyncMoneyWithDatabaseController(this)
        }

    init {
        val user: FirebaseUser? = Database.auth.currentUser
        if (user != null) {
            name = user.displayName.toString()
            email = user.email.toString()
            uid =  user.uid
        }
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
        return IAttack.SECONDS_BETWEEN_ATTACKS -difinSec.toInt()
    }

    fun attack() : Double {
        return attackBuilding.value
    }

    fun addClickMoney() {
       money += moneyPerSecond().toInt()
    }

    fun addMoneySinceLastSynched(lastSynched: Date = this.lastSynched) {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastSynched.getTime()
        this.lastSynched = synchedNow
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        money += difinSec.times(moneyPerSecond()).toInt()
    }

    override fun addMoneySinceLastSynchedExternally(lastSynched: Date) {
        addMoneySinceLastSynched(lastSynched)
        Database.forceMoneySync(this)
    }

    fun buyBuilding(building: IBuilding) : Boolean {
        if(building.calculateUpgradeCost() < money) {
            money -= building.calculateUpgradeCost().toLong()
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