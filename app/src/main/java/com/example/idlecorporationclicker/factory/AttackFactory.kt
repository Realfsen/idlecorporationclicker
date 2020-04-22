package com.example.idlecorporationclicker.factory

import com.example.idlecorporationclicker.attacks.SabotageAttack
import com.example.idlecorporationclicker.attacks.StealAttack
import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.model.ATTACK_TYPE

class AttackFactory : AbstractFactory {

    override fun <T, ATTACK_TYPE> create(b : ATTACK_TYPE) : T {
       when(b) {
            com.example.idlecorporationclicker.model.ATTACK_TYPE.STEAL -> return StealAttack() as T
            com.example.idlecorporationclicker.model.ATTACK_TYPE.SABOTAGE-> return SabotageAttack() as T
        }
        return null as T
    }

}


