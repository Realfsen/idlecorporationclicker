package com.example.idlecorporationclicker.model

import com.example.idlecorporationclicker.buildings.AttackBuilding

interface IAttack {
    val type: ATTACK_TYPE
    val name: String
    var value: Int
    fun calculateAttackValue() : Int
    fun doAttack() : Boolean
}

enum class ATTACK_TYPE {
    NONE, STEAL, SABOTAGE
}