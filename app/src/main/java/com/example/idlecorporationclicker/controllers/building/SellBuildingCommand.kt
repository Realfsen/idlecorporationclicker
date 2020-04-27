package com.example.idlecorporationclicker.controllers.building

import com.example.idlecorporationclicker.controllers.ICommand
import com.example.idlecorporationclicker.models.building.IBuilding
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.views.ScreenTemplate

class SellBuildingCommand(player: Player, building: IBuilding, var screen: ScreenTemplate) :
    ICommand {
    private var player = player
    private var building = building

    override fun Execute() {
        player.sellBuilding(building)
        screen.update()
    }

    override fun CanExecute(): Boolean {
        return building.level > 1
    }

    override fun Undo() {
        player.buyBuilding(building)
    }

}