package com.example.idlecorporationclicker.models.attack

import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player

interface IAttack {
    val type: ATTACK_TYPE
    val name: String
    fun calculateAttackValue(player : Player) : Double
    fun doAttack(player: Player, defender: IPlayer) : Boolean
    fun calculateSuccess(player: Player, defender: IPlayer) : Int
    companion object{
        var SECONDS_BETWEEN_ATTACKS : Int = 120
    }
}


enum class ATTACK_TYPE {
    NONE, STEAL, SABOTAGE
}

class ATTACK_DESCRIPTION  {
    companion object {
       val NONE = "You have to choose an attack to either steal or sabotage your enemy"
       val STEAL = "Steal from an enemy! Launch an successful attack and you will be richer while your enemy will be poorer"
       val SABOTAGE = "Sabotage your enemy! If successful your enemy will weep and his income destroyed"

        fun getText(type : ATTACK_TYPE) : String {
            when(type) {
                ATTACK_TYPE.NONE -> return NONE
                ATTACK_TYPE.STEAL -> return STEAL
                ATTACK_TYPE.SABOTAGE -> return SABOTAGE
            }
        }
    }
}

