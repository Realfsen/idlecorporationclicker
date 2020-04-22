package com.example.idlecorporationclicker.attacks

import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.model.ATTACK_TYPE
import com.example.idlecorporationclicker.model.IAttack

class StealAttack(override var attackBuildings: List<AttackBuilding>) : IAttack {
    override val type: ATTACK_TYPE = ATTACK_TYPE.STEAL
    override val name: String = "Steal"
    override var value: Int = calculateAttackValue()

    override fun calculateAttackValue(): Int {
        return 10
    }

}