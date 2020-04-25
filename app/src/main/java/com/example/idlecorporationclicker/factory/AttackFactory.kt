package com.example.idlecorporationclicker.factory

import com.example.idlecorporationclicker.models.attack.SabotageAttack
import com.example.idlecorporationclicker.models.attack.StealAttack

class AttackFactory : AbstractFactory {

    override fun <T, ATTACK_TYPE> create(b : ATTACK_TYPE) : T {
       when(b) {
            com.example.idlecorporationclicker.models.attack.ATTACK_TYPE.STEAL -> return StealAttack() as T
            com.example.idlecorporationclicker.models.attack.ATTACK_TYPE.SABOTAGE-> return SabotageAttack() as T
        }
        return null as T
    }

}


