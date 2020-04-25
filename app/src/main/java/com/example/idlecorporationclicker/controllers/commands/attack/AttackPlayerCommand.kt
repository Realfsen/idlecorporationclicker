package com.example.idlecorporationclicker.controllers.commands.attack

import com.example.idlecorporationclicker.controllers.commands.ICommand
import com.example.idlecorporationclicker.models.attack.IAttack
import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.views.ScreenTemplate
import java.util.*

class AttackPlayerCommand(attacker: Player, defender: IPlayer, attack: IAttack, var screen : ScreenTemplate) :
    ICommand {
    private var attacker = attacker
    private var defender = defender
    private var attack = attack

    override fun Execute() {
        attack.doAttack(attacker, defender)
        attacker.lastAttack = Date()
        screen.update()
    }

    override fun CanExecute(): Boolean {
        return attacker.canAttack()
    }

    override fun Undo() {
    }

}