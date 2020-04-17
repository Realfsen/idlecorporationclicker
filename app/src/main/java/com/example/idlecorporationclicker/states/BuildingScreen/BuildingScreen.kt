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
    private var buyBtn1: TextButton
    private var buyBtn2: TextButton
    private var buyBtn3: TextButton
    private var background : Texture
    private var incomeBuilding1: Image
    private var incomeBuilding2: Image
    private var incomeBuilding3: Image
    private var incomeBuildingTable1: HorizontalGroup
    private var incomeBuildingTable2: HorizontalGroup
    private var incomeBuildTableUpgrade1 : HorizontalGroup
    private var incomeBuildTableUpgrade2 : HorizontalGroup
    private var numberOfBuildings1 : Int
    private var numberOfBuildings2 : Int
    private var numberOfBuildings3 : Int
    private var wholeGroup : Table

    private var stage : Stage

    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-basemdpi.png"))
        incomeBuilding1 = Image(Texture(Gdx.files.internal("buildings/income/base/1x/basemdpi.png")))
        incomeBuilding2 = Image(Texture(Gdx.files.internal("buildings/income/1x/building2mdpi.png")))
        incomeBuilding3 = Image(Texture(Gdx.files.internal("buildings/income/1x/building3mdpi.png")))


        incomeBuildingTable1 = HorizontalGroup()
        incomeBuildingTable2 = HorizontalGroup()
        incomeBuildTableUpgrade1 = HorizontalGroup()
        incomeBuildTableUpgrade2 = HorizontalGroup()
        wholeGroup = Table()
        numberOfBuildings1 = 0
        numberOfBuildings2 = 1
        numberOfBuildings3 = 1


        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)
        buyBtn1 = TextButton("Buy", uiSkin)

        buyBtn1.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                numberOfBuildings1++
                wholeGroup.clear()
                buildAllTowers()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        buyBtn2 = TextButton("Buy", uiSkin)

        buyBtn2.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                numberOfBuildings2++
                wholeGroup.clear()
                buildAllTowers()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        buyBtn3 = TextButton("Buy", uiSkin)

        buyBtn3.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                numberOfBuildings3++
                wholeGroup.clear()
                buildAllTowers()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })
        buildAllTowers()
        wholeGroup.top()
        wholeGroup.setFillParent(true)

        stage = Stage(ScreenViewport(cam))
        stage.addActor(wholeGroup)
        Gdx.input.setInputProcessor(stage)
    }

    fun buildTowers(imgPath : String, number : Int, btn : TextButton, price: Int, upgrade: Int) {
        for (i in 0..number) {
            var verticalGroup = VerticalGroup()
            verticalGroup.addActor(incomeBuilding(imgPath, price, upgrade).getBuilding())
            wholeGroup.add(verticalGroup)
        }
        wholeGroup.add(btn)
        wholeGroup.row().padTop(15f)
    }

    fun buildAllTowers() {
        buildTowers("buildings/income/base/1x/basemdpi.png", numberOfBuildings1, buyBtn1, 30, 30)
        buildTowers("buildings/income/1x/building2mdpi.png", numberOfBuildings2, buyBtn2, 40, 10)
        buildTowers("buildings/income/1x/building3mdpi.png", numberOfBuildings3, buyBtn3, 100, 94)
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

class incomeBuilding (imgPath: String, price: Int, upgrade: Int) {
    public var incomeBuilding: Image
    public var buyBtn : TextButton
    public var upgradeStr : String
    public var upgradePrice : Int

    init {
        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)

        upgradePrice = price;
        upgradeStr = "Upgrade\n"
        buyBtn = TextButton(upgradeStr+upgradePrice, uiSkin)
        incomeBuilding = Image(Texture(Gdx.files.internal(imgPath)))
        buyBtn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                upgradePrice += upgrade;
                buyBtn.setText(upgradeStr+upgradePrice);
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })
    }

    fun getBuilding(): Table {
        var table = Table()
        table.add(incomeBuilding).padLeft(15f).row()
        table.add(buyBtn)
        table.setFillParent(true)
        return table
    }

}
