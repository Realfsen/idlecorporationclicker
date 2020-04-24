package com.example.idlecorporationclicker.commands

import com.example.idlecorporationclicker.model.IBuilding
import com.example.idlecorporationclicker.model.Player

class BuyBuildingCommand(player: Player, building: IBuilding) : ICommand {
    private var player = player
    private var building = building

    override fun Execute() {
        player.buyBuilding(building)
    }

    override fun CanExecute(): Boolean {
        return player.hasMoneyForBuilding(building)
    }

    override fun Undo() {
        player.sellBuilding(building)
    }

}