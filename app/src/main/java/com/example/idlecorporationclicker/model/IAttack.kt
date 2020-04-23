package com.example.idlecorporationclicker.model

interface IAttack {
    val type: ATTACK_TYPE
    val name: String
    fun calculateAttackValue(player : Player) : Double
    fun doAttack(player: Player, defender: Player) : Boolean
    fun calculateSuccess(player: Player, defender: Player) : Int
}

enum class ATTACK_TYPE {
    NONE, STEAL, SABOTAGE
}