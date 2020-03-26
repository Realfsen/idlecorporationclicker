package com.example.idlecorporationclicker.model

/* Defines the AbstractFactory interface*/
interface AbstractFactory {

    fun createBuilding(type: BuildingType)
}