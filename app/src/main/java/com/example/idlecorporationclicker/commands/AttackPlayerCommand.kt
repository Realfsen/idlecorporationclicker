package com.example.idlecorporationclicker.commands

import com.example.idlecorporationclicker.model.IAttack
import com.example.idlecorporationclicker.model.IBuilding
import com.example.idlecorporationclicker.model.Player
import java.util.*

class AttackPlayerCommand(attacker: Player, defender: Player, attack: IAttack) : ICommand {
    private var attacker = attacker
    private var defender = defender
    private var attack = attack

    override fun Execute() {
        attack.doAttack(attacker, defender)
        attacker.lastAttack = Date()
    }

    override fun CanExecute(): Boolean {
        return attacker.canAttack()
    }

    override fun Undo() {
    }

}