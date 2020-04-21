package com.example.idlecorporationclicker.states.BuildingScreen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.EventListener
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.buildings.AttackBuilding
import com.example.idlecorporationclicker.buildings.DefenseBuilding
import com.example.idlecorporationclicker.buildings.IncomeBuilding
import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.State

class BuildingScreen(override var game: Game, override var gsm: GameStateManager) : State(gsm, game) {


    private var IncomeBuyButton: TextButton
    private var AttackBuyButton: TextButton
    private var DefenseBuyButton: TextButton
    private var background : Texture
    private var wholeGroup : Table
    private var batch : SpriteBatch
    private var stage: Stage
    private var uiSkin : Skin


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-basemdpi.png"))

        wholeGroup = Table()

        stage = Stage(ScreenViewport())
        batch = SpriteBatch()

        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)
        IncomeBuyButton = TextButton("Buy", uiSkin)

        IncomeBuyButton.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                wholeGroup.clear()
                gsm.player.buyBuilding(BuildingType.INCOME)
                buildAllTowers()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        AttackBuyButton = TextButton("Buy", uiSkin)

        AttackBuyButton.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                wholeGroup.clear()
                gsm.player.buyBuilding(BuildingType.ATTACK)
                buildAllTowers()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        DefenseBuyButton = TextButton("Buy", uiSkin)

        DefenseBuyButton.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                wholeGroup.clear()
                gsm.player.buyBuilding(BuildingType.DEFENSE)
                buildAllTowers()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        buildAllTowers()
        wholeGroup.top()
        wholeGroup.setFillParent(true)
        stage.addActor(wholeGroup)
    }


    fun <T>buildBuildings(building : List<IBuilding>, buttonText : String, btn : TextButton, showBuyButton : Boolean) {
        var horizontalGroup = HorizontalGroup()
        building.forEach {
            var verticalGroup = VerticalGroup().padLeft(5f)
            verticalGroup.addActor(buildBuildingClass(it, buttonText).getBuilding())
            horizontalGroup.addActor(verticalGroup)
        }
        if(showBuyButton) {
            horizontalGroup.addActor(btn)
        }
        wholeGroup.add(horizontalGroup).expandX()
        wholeGroup.row().padTop(15f).fill()
    }


    public fun buildAllTowers() {
       var defenseBuildings = gsm.player.defenseBuilding
        var attackBuildings = gsm.player.attackBuildings
        var incomeBuildings = gsm.player.passiveIncomeBuildings
        var showIncomeButton = true
        var showDefenseButton= true
        var showAttackButton = true
        if(incomeBuildings.size == 4) {
            showIncomeButton = false
        }
        if(defenseBuildings.size == 4) {
            showDefenseButton = false
        }
        if(attackBuildings.size == 4) {
            showAttackButton = false
        }
        buildBuildings<DefenseBuilding>(defenseBuildings, "Defense", DefenseBuyButton, showDefenseButton)
        buildBuildings<IncomeBuilding>(incomeBuildings, "Income", IncomeBuyButton, showIncomeButton)
        buildBuildings<AttackBuilding>(attackBuildings, "Attack", AttackBuyButton, showAttackButton)
    }

    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    override fun render(dt : Float) {
        handleInput()
        batch.begin()
        batch.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        batch.end()
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }
    override fun hide() {
    }

    override fun show() {
        Gdx.input.setInputProcessor(stage)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        batch.dispose()
        stage.dispose()
    }
}

class buildBuildingClass(building : IBuilding, buttonText : String) {
    private var buildingImage : Image
    private var btn : TextButton
    private var uiSkin : Skin

    init {
        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(2f)

        buildingImage = building.image
        btn = TextButton(buttonText+": "+building.value+"\nLevel: "+building.level+"\nUpgrade Cost: "+building.upgradeCost, uiSkin)
        btn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                building.upgrade()
                btn.setText(buttonText+": "+building.value+"\nLevel: "+building.level+"\nUpgrade Cost: "+building.upgradeCost)
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })
    }

    fun getBuilding(): Table {
        var table = Table()
        table.add(buildingImage).padLeft(15f).fill()
            table.row()
        table.add(btn).padLeft(15f).fill()
        return table
    }

}
