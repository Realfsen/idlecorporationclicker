package com.example.idlecorporationclicker.attacks

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.model.AttackType
import com.example.idlecorporationclicker.model.IAttack

class SabotageAttack(override var attackBuildings : List<AttackBuilding>) : IAttack{
    override val type: AttackType = AttackType.STEAL
    override val name: String = "Steal"
    override var value: Int = calculateAttackValue()

    override fun calculateAttackValue(): Int {
        return 10
    }
}