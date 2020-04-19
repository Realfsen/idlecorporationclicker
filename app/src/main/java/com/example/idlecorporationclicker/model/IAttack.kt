package com.example.idlecorporationclicker.model

import com.example.idlecorporationclicker.buildings.AttackBuilding

interface IAttack {
    val type: AttackType
    val name: String
    var value: Int
    var attackBuildings: List<AttackBuilding>
    fun calculateAttackValue() : Int
}

enum class AttackType {
    STEAL, SABOTAGE
}