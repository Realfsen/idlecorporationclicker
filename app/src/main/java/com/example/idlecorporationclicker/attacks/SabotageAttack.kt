package com.example.idlecorporationclicker.attacks

import com.example.idlecorporationclicker.model.ATTACK_TYPE
import com.example.idlecorporationclicker.model.IAttack
import com.example.idlecorporationclicker.model.Player

class SabotageAttack : IAttack{

    override val type: ATTACK_TYPE = ATTACK_TYPE.STEAL
    override val name: String = "Steal"

    override fun calculateAttackValue(player: Player): Double {
        return player.attack()
    }

    override fun calculateSuccess(player: Player, defender: Player): Int {
        val attackValue = player.attack()
        val defenseValue = defender.defense()
        val ratio = attackValue / defenseValue
        return (ratio*100).toInt()
    }

    override fun doAttack(player: Player, defender: Player): Boolean {
        TODO("Not yet implemented")
    }

}