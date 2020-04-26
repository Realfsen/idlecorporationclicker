package com.example.idlecorporationclicker.models.database

import com.example.idlecorporationclicker.models.player.Player

interface IDatabase {
    fun initiateLocalPlayer(player: Player)
    fun buildingUpdateIncome()
    fun buildingUpdateAttack()
    fun buildingUpdateDefense()
    fun updateTimeLastSyncedInDatabase()
    fun start() {}
}