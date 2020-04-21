package com.example.idlecorporationclicker.buildings

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding

class DefenseBuilding (override var level : Int ) : IBuilding {
    override val type: BuildingType
    override val name: String
    override var value: Int = calculateValue()
    override var upgradeCost: Int = calculateUpgradeCost()
    override val image : Image

    init {
        type = BuildingType.DEFENSE;
        name = "Defense Building"
        image = Image(Texture(Gdx.files.internal("buildings/income/1x/building3mdpi.png")))
    }

    override fun calculateValue() : Int {
        return 10
    }
    override  fun calculateUpgradeCost() : Int {
        return 10
    }

    override fun upgrade() {
        level++
    }

}