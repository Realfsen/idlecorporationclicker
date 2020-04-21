package com.example.idlecorporationclicker.factory

import com.example.idlecorporationclicker.model.BuildingType


/* Defines the AbstractFactory interface*/
interface AbstractFactory {
    fun <T, B> create(b : B) : T
}

enum class CreationType {
    BUILDING, ATTACK
}
