package com.example.idlecorporationclicker.factory

import com.example.idlecorporationclicker.model.BuildingType


/* Defines the AbstractFactory interface*/
interface AbstractFactory {
    fun <T> create() : T
}

enum class CreationType {
    BUILDING, ATTACK
}
