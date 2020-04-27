package com.example.idlecorporationclicker.models.player

import com.example.idlecorporationclicker.models.building.IBuilding
import com.example.idlecorporationclicker.models.database.Database
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

    fun stealFromThisPlayer(stealPercentage: Double) : Long {
        if (stealPercentage > 1 || stealPercentage < 0) return -1
        val amount :Long = (money * stealPercentage).toLong()
        Database.stealFromPlayer(this, amount)
        return amount
    }

    fun getStolenMoney(money: Long) {
        this.money += money
        Database.forceMoneySync()
    }
}