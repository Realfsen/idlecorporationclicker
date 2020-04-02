package com.example.idlecorporationclicker.model


/* Defines the AbstractFactory interface*/
interface AbstractFactory {

    abstract var newB: Building

    fun create(type: CreationType, bType: BuildingType)
}

enum class CreationType {
    BUILDING, ATTACK
}
enum class BuildingType {
    ATTACK, DEFENSE, INCOME
}
