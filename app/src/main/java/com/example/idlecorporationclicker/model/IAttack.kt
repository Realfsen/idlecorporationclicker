package com.example.idlecorporationclicker.model

interface IAttack {
    val type: AttackType
    val name: String
    var value: Int
}

enum class AttackType {
    STEAL, SABOTAGE
}