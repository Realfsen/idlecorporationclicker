package com.example.idlecorporationclicker.commands

import com.example.idlecorporationclicker.model.IBuilding
import com.example.idlecorporationclicker.model.Player

class SellBuildingCommand(player: Player, building: IBuilding) : ICommand {
    private var player = player
    private var building = building

    override fun Execute() {
        player.sellBuilding(building)
    }

    override fun CanExecute(): Boolean {
        return building.level > 1
    }

    override fun Undo() {
        player.buyBuilding(building)
    }

}