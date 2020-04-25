package com.example.idlecorporationclicker.models.database

import com.example.idlecorporationclicker.models.player.Player

interface IDatabaseController {
    fun initiateLocalPlayer(player: Player)
    fun buildingUpdateIncome()
    fun buildingUpdateAttack()
    fun buildingUpdateDefense()
    fun updateTimeLastSyncedInDatabase()
}