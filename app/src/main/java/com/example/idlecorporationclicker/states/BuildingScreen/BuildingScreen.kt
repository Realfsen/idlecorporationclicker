package com.example.idlecorporationclicker.states.BuildingScreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.MainScreen.MainScreen
import com.example.idlecorporationclicker.states.State

class BuildingScreen(override var gsm: GameStateManager) : State(gsm) {
    private var buyBtn: TextButton
    private var background : Texture
    private var incomeBuilding1: Image
    private var incomeBuilding2: Image
    private var incomeBuildingTable1: HorizontalGroup
    private var incomeBuildingTable2: HorizontalGroup
    private var incomeBuildTableUpgrade1 : HorizontalGroup
    private var incomeBuildTableUpgrade2 : HorizontalGroup
    private var numberOfBuildings1 : Int
    private var wholeGroup : Table

    private var stage : Stage

    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-basemdpi.png"))
        incomeBuilding1 = Image(Texture(Gdx.files.internal("buildings/income/base/1x/basemdpi.png")))
        incomeBuilding2 = Image(Texture(Gdx.files.internal("buildings/income/base/1x/basemdpi.png")))


        incomeBuildingTable1 = HorizontalGroup()
        incomeBuildingTable2 = HorizontalGroup()
        incomeBuildTableUpgrade1 = HorizontalGroup()
        incomeBuildTableUpgrade2 = HorizontalGroup()
        wholeGroup = Table()
        numberOfBuildings1 = 1


        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(2f)
        buyBtn = TextButton("Buy", uiSkin)

        buyBtn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                numberOfBuildings1++
                wholeGroup.clear()
                buildTowers()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })


        buildTowers()
        wholeGroup.top()
        wholeGroup.setFillParent(true)

        stage = Stage(ScreenViewport(cam))
        stage.addActor(wholeGroup)
        Gdx.input.setInputProcessor(stage)
    }

    fun buildTowers() {
        for (i in 0..numberOfBuildings1) {
            var verticalGroup = VerticalGroup()
            verticalGroup.addActor(incomeBuilding().getBuilding())
            wholeGroup.add(verticalGroup)
        }
        wholeGroup.add(buyBtn)
    }


    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        sb.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        sb.end()
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }

}

class incomeBuilding () {

    public var incomeBuilding: Image
    public var buyStr : Label
    public var upgradeStr : Label


    init {
        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(2f)

        buyStr = Label("Buy: 30", uiSkin)
        upgradeStr = Label("Upgrade: 30", uiSkin)
        incomeBuilding = Image(Texture(Gdx.files.internal("buildings/income/base/1x/basemdpi.png")))
        incomeBuilding.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                println("Hit attack!")
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

    }

    fun getBuilding(): Table {
        var table = Table()
        table.add(incomeBuilding).row()
        table.add(upgradeStr)
        table.setFillParent(true)
        return table;
    }

}
