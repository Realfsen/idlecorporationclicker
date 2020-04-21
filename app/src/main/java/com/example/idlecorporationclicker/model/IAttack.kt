package com.example.idlecorporationclicker.model

import com.example.idlecorporationclicker.buildings.AttackBuilding

interface IAttack {
    val type: ATTACK_TYPE
    val name: String
    var value: Int
    var attackBuildings: List<AttackBuilding>
    fun calculateAttackValue() : Int
}

enum class ATTACK_TYPE {
    NONE, STEAL, SABOTAGE
}