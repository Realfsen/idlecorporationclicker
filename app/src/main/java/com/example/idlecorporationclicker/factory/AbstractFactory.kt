package com.example.idlecorporationclicker.factory


/* Defines the AbstractFactory interface*/
interface AbstractFactory {
    fun <T, B> create(b : B) : T
}

enum class CREATION_TYPE {
    BUILDING, ATTACK
}
