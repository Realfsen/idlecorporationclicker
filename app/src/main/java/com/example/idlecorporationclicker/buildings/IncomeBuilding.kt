package com.example.idlecorporationclicker.buildings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding
import com.example.idlecorporationclicker.model.IBuildingUtils
import kotlin.math.pow

class IncomeBuilding(override var level : Double) : IBuilding{
    override val image: Image
    override var baseCost: Double = 10.0
    override var baseValue: Double = 15.0
    override val type: BuildingType
    override val name: String
    override var value: Double = calculateValue()
    override var upgradeCost: Double = calculateUpgradeCost()

    init {
        type = BuildingType.INCOME;
        name = "Income Building"
        image = Image(Texture(Gdx.files.internal("buildings/income/base/0.75x/baseldpi.png")))
    }

    override fun calculateValue() : Double{
        return IBuildingUtils.calculateValue(baseValue, level)
    }
    override fun calculateUpgradeCost() : Double{
        return IBuildingUtils.calculateUpgradeCost(baseCost, level)
    }

    override fun upgrade() {
        level++
        value = calculateValue()
        upgradeCost = calculateUpgradeCost()

    }

}