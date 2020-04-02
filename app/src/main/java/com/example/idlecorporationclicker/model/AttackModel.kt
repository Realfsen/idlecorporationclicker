package com.example.idlecorporationclicker.model

interface AttackModel {
    val type: AttackType
    val aName: String
    var aValue: Int

    fun attack()
}

enum class AttackType {
    STEAL, SABOTAGE
}