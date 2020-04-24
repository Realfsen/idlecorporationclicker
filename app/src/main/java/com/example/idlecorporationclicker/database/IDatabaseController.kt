package com.example.idlecorporationclicker.database

import com.example.idlecorporationclicker.model.Player

interface IDatabaseController {
    fun initiateLocalPlayer(player: Player)
    fun buildingUpdateIncome()
    fun buildingUpdateAttack()
    fun buildingUpdateDefense()
    fun updateTimeLastSyncedInDatabase()
}