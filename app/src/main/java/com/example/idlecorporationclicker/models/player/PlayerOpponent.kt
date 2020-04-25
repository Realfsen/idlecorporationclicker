package com.example.idlecorporationclicker.models.player

import com.example.idlecorporationclicker.models.building.AttackBuilding
import com.example.idlecorporationclicker.models.building.DefenseBuilding
import com.example.idlecorporationclicker.models.building.IncomeBuilding
import com.example.idlecorporationclicker.factory.BuildingFactory
import com.example.idlecorporationclicker.factory.FACTORY_TYPE
import com.example.idlecorporationclicker.factory.FactoryProvider
import com.example.idlecorporationclicker.models.building.BuildingType
import com.example.idlecorporationclicker.models.building.IBuilding
import java.util.*

class PlayerOpponent(
    var uid: String,
    override var name: String,
    override var money: Long,
    override var lastSynched: Date
) : IPlayer {
    val factory = FactoryProvider()
    val buildingFactory : BuildingFactory =  factory.getFactory(FACTORY_TYPE.BUILDING) as BuildingFactory
    override var attackBuilding: IBuilding = buildingFactory.create<IncomeBuilding, BuildingType>(
        BuildingType.INCOME)
    override var defenseBuilding: IBuilding = buildingFactory.create<AttackBuilding, BuildingType>(
        BuildingType.ATTACK)
    override var passiveIncomeBuilding: IBuilding = buildingFactory.create<DefenseBuilding, BuildingType>(
        BuildingType.DEFENSE)
}