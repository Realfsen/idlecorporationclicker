package com.example.idlecorporationclicker.model

interface IAttack {
    val type: ATTACK_TYPE
    val name: String
    fun calculateAttackValue(player : Player) : Double
    fun doAttack(player: Player, defender: Player) : Boolean
    fun calculateSuccess(player: Player, defender: Player) : Int
    object companion {
        var SECONDS_BETWEEN_ATTACKS : Int = 5
    }
}


enum class ATTACK_TYPE {
    NONE, STEAL, SABOTAGE
}