package com.example.idlecorporationclicker.buildings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding
import com.example.idlecorporationclicker.model.IBuildingUtils
import java.lang.Math.pow
import kotlin.math.pow

class DefenseBuilding (override var level : Double) : IBuilding {
    override val type: BuildingType
    override val name: String
    override var value: Double = calculateValue()
    override var upgradeCost: Double = calculateUpgradeCost()
    override val image : Image
    override var baseCost: Double = 50.0
    override var baseValue: Double = 17.0

    init {
        type = BuildingType.DEFENSE;
        name = "Defense Building"
        image = Image(Texture(Gdx.files.internal("buildings/income/0.75x/building3ldpi.png")))
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

    override fun sell() {
        level--
        value = calculateValue()
        upgradeCost = calculateUpgradeCost()
    }

    override fun upgrade() {
        level++
        value = calculateValue()
        upgradeCost = calculateUpgradeCost()

    }

}