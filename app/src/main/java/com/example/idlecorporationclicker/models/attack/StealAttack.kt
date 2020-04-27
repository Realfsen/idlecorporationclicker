package com.example.idlecorporationclicker.models.attack

import com.example.idlecorporationclicker.models.attack.ATTACK_TYPE
import com.example.idlecorporationclicker.models.attack.IAttack
import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player
import kotlin.random.Random

class StealAttack : IAttack {
    override val type: ATTACK_TYPE = ATTACK_TYPE.STEAL
    override val name: String = "Steal"

    override fun calculateAttackValue(player: Player): Double {
        return player.attack()
    }

    override fun calculateSuccess(player: Player, defender: IPlayer): Int {
        val attackValue = player.attack()
        println(attackValue)
        val defenseValue = defender.defense()
        println(defenseValue)
        val ratio : Double = attackValue / defenseValue
        println(ratio)
        if (ratio > 1) return 99
        if (ratio < 0.001) return 1
        return (ratio*100).toInt()
    }

    override fun doAttack(player: Player, defender: IPlayer): Boolean {
        val successChance = calculateSuccess(player, defender)
        val att = Random.nextInt(0, 100)
        if (att < successChance) doSteal(player, defender)
        return true
    }

    private fun doSteal(player: Player, defender: IPlayer) {
        player.getStolenMoney(defender.stealFromThisPlayer(0.7))
    }

}