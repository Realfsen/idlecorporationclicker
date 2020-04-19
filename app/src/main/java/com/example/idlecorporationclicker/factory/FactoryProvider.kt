package com.example.idlecorporationclicker.factory

class FactoryProvider {
    fun getFactory(type : FACTORY_TYPE) : AbstractFactory {
        when(type) {
            FACTORY_TYPE.ATTACK -> return AttackFactory()
            FACTORY_TYPE.BUILDING -> return BuildingFactory()
        }
    }
}

enum class FACTORY_TYPE {
   ATTACK,
    BUILDING
}