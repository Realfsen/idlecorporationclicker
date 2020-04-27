package com.example.idlecorporationclicker.models.player

import com.example.idlecorporationclicker.models.building.AttackBuilding
import com.example.idlecorporationclicker.models.building.DefenseBuilding
import com.example.idlecorporationclicker.models.building.IncomeBuilding
import com.example.idlecorporationclicker.factory.BuildingFactory
import com.example.idlecorporationclicker.factory.FACTORY_TYPE
import com.example.idlecorporationclicker.factory.FactoryProvider
import com.example.idlecorporationclicker.models.building.BuildingType
import com.example.idlecorporationclicker.models.building.IBuilding
import com.example.idlecorporationclicker.models.database.Database
import java.util.*
import java.util.concurrent.TimeUnit

class PlayerOpponent(
    override var uid: String,
    override var name: String,
    override var money: Long,
    override var lastSynched: Date
) : IPlayer {
    override var email: String = ""
    override var nextTimeToSync: Long = Date().time + Database.SYNC_DELAY_SECONDS
    val factory = FactoryProvider()
    val buildingFactory : BuildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory
    override var attackBuilding: IBuilding = buildingFactory.create<AttackBuilding, BuildingType>(
        BuildingType.ATTACK)
    override var defenseBuilding: IBuilding = buildingFactory.create<DefenseBuilding, BuildingType>(
        BuildingType.DEFENSE)
    override var passiveIncomeBuilding: IBuilding = buildingFactory.create<IncomeBuilding, BuildingType>(
        BuildingType.INCOME)

    override fun addMoneySinceLastSynchedExternally(lastSynched: Date) {
        var synchedNow = Date()
        var timeDifference = synchedNow.getTime() - lastSynched.getTime()
        this.lastSynched = synchedNow
        var difinSec = TimeUnit.MILLISECONDS.toSeconds(timeDifference)
        money += difinSec.times(moneyPerSecond()).toInt()
    }
}