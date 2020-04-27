package com.example.idlecorporationclicker.models.building

import android.util.Log
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.example.idlecorporationclicker.models.database.Database

class AttackBuilding(override var level: Double)
 : IBuilding {
    override val image: Image
    override var baseCost: Double = 15.00
    override var baseValue: Double = 15.00
    override val type: BuildingType
    override val name: String
    override var value: Double = calculateValue()
    override var upgradeCost: Double = calculateUpgradeCost()

    init {
        type = BuildingType.ATTACK;
        name = "Attack Building"
//        image = Image(Texture(Gdx.files.internal("buildings/attack/base/0.75x/baseldpi.png")))
        image = Image(Texture(Gdx.files.internal("attacks/1x/dualstealmdpi.png")))
    }

    override fun calculateValue() : Double{
        return IBuildingUtils.calculateValue(baseValue, level)
    }
    override fun calculateUpgradeCost() : Double{
        return IBuildingUtils.calculateUpgradeCost(baseCost, level)
    }

    override fun sellValue(): Double {
        return IBuildingUtils.calculateUpgradeCost(baseCost, level-1)
    }

    override fun downgrade() {
        level--
        value = calculateValue()
        upgradeCost = calculateUpgradeCost()
    }

    override fun upgrade() {
        level++
        value = calculateValue()
        upgradeCost = calculateUpgradeCost()
        Database.buildingUpdateAttack()
        Log.d("lvl", level.toString())
    }
}