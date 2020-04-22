package com.example.idlecorporationclicker.buildings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding
import java.lang.Math.pow
import kotlin.math.pow

class DefenseBuilding (override var level : Double) : IBuilding {
    override val type: BuildingType
    override val name: String
    override var value: Double = calculateValue()
    override var upgradeCost: Double = calculateUpgradeCost()
    override val image : Image

    init {
        type = BuildingType.DEFENSE;
        name = "Defense Building"
        image = Image(Texture(Gdx.files.internal("buildings/income/0.75x/building3ldpi.png")))
    }

    override fun calculateValue() : Double{
        return level*level
    }
    override  fun calculateUpgradeCost() : Double{
        return 2.0.pow(level)
    }

    override fun upgrade() {
        level++
        value = calculateValue()
        upgradeCost = calculateUpgradeCost()

    }

}