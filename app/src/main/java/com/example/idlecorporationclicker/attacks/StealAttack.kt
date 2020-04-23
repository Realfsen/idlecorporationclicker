package com.example.idlecorporationclicker.attacks

import com.example.idlecorporationclicker.model.ATTACK_TYPE
import com.example.idlecorporationclicker.model.IAttack
import com.example.idlecorporationclicker.model.Player
import kotlin.random.Random

class StealAttack : IAttack {
    override val type: ATTACK_TYPE = ATTACK_TYPE.STEAL
    override val name: String = "Steal"

    override fun calculateAttackValue(player: Player): Double {
        return player.attack()
    }

    override fun calculateSuccess(player: Player, defender: Player): Int {
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

    override fun doAttack(player: Player, defender: Player): Boolean {
        val successChance = calculateSuccess(player, defender)
        val att = Random.nextInt(0, 100)
        if (att < successChance) doSteal(player, defender)
        return true
    }

    private fun doSteal(player: Player, defender: Player) {
        player.income = (player.income * 1.3).toInt()
        defender.income = (defender.income * 0.7).toInt()
    }

}