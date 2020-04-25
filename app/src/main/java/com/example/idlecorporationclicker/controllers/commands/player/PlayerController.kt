package com.example.idlecorporationclicker.controllers.commands.player

import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.views.ScreenTemplate

class PlayerController(var player : Player, var screen : ScreenTemplate) {

    fun addMoneyByClick() {
        player.addClickMoney()
        screen.update()
    }

    fun addMoneySinceLastSynch() {
        player.addMoneySinceLastSynched()
        screen.update()
    }
}