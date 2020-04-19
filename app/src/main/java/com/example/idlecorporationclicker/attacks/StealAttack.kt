package com.example.idlecorporationclicker.attacks

import com.example.idlecorporationclicker.model.AttackType
import com.example.idlecorporationclicker.model.IAttack

class StealAttack(override val type: AttackType, override val name: String, override var value: Int) : IAttack{

}