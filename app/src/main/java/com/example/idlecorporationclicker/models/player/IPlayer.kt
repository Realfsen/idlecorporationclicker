package com.example.idlecorporationclicker.models.player

import com.example.idlecorporationclicker.models.building.IBuilding
import java.util.*

interface IPlayer {
    var uid: String
    var name : String
    var lastSynched : Date
    var attackBuilding : IBuilding
    var defenseBuilding : IBuilding
    var passiveIncomeBuilding: IBuilding
    var money : Long

    fun addMoneySinceLastSynchedExternally(lastSynched: Date)

    fun defense() : Double {
        return defenseBuilding.value
    }

    fun moneyPerSecond() : Double {
        return passiveIncomeBuilding.value
    }
}