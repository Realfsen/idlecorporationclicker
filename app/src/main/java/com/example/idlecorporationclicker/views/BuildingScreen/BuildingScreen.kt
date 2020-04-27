package com.example.idlecorporationclicker.states.BuildingScreen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.controllers.building.BuyBuildingCommand
import com.example.idlecorporationclicker.controllers.player.PlayerController
import com.example.idlecorporationclicker.models.building.BuildingType
import com.example.idlecorporationclicker.models.building.IBuilding
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.views.actors.MenuActor
import com.example.idlecorporationclicker.views.ScreenTemplate

class BuildingScreen(override var game: Game, override var gsm: GameStateManager) : ScreenTemplate(gsm, game) {

    private val screenHeight = Gdx.graphics.height.toFloat()
    private val screenWidth = Gdx.graphics.width.toFloat()
    private var startTime: Long
    private var moneyStr: Label
    private var background : Texture
    private var wholeGroup : Table
    private var batch : SpriteBatch
    private var stage: Stage
    private var uiSkin : Skin
    private var playerController : PlayerController
    private var menuActor : MenuActor

    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-basemdpi.png"))
        stage = Stage(ScreenViewport(cam))
        batch = SpriteBatch()
        wholeGroup = Table()
        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        moneyStr = Label("Cash: "+gsm.player.money, fontStyle)
        startTime = TimeUtils.nanoTime()
        playerController = PlayerController(gsm.player, this)

        buildStatsTable()
        buildAllBuildings()
        wholeGroup.setFillParent(true)
        wholeGroup.top()
        stage.addActor(wholeGroup)
        menuActor =
            MenuActor(gsm)
        stage.addActor(menuActor.getActor())
        generateTopBar(stage)
    }

    fun buildingTemplate(building : IBuilding, type: BuildingType, labelPrefix : String) : HorizontalGroup {
        val buttonGfx = TextureRegion(buttons, 170, 430, 430, 195)
        val buttonDown = TextureRegion(buttons, 625, 430, 430, 195)
        val buttonDisabled = TextureRegion(buttons, 1540, 430, 430, 195)
        val newBuyButton = Image(buttonGfx)
        val newBuyButtonDown = Image(buttonDown)
        val newBuyButtonDisabled = Image(buttonDisabled)
//        newBuyButton.setPosition(500f, 500f)
        val incCost = Label("$ "+building.upgradeCost.toInt(), fontStyle)
        val downCost = Label("$ "+building.upgradeCost.toInt(), fontStyle)
        val disCost = Label("$ "+building.upgradeCost.toInt(), fontStyle)
        val buttonGrp = Group()
        buttonGrp.addActor(newBuyButton)
        buttonGrp.addActor(incCost)
        incCost.setPosition(75f, 75f)
        buttonGrp.setX(20f)

        val buttonGrpDown = Group()
        buttonGrpDown.addActor(newBuyButtonDown)
        buttonGrpDown.addActor(downCost)
        downCost.setPosition(75f, 75f)

        val buttonGrpDisabled = Group()
        buttonGrpDisabled.addActor(newBuyButtonDisabled)
        buttonGrpDisabled.addActor(disCost)
        disCost.setPosition(75f, 75f)


        val buyBuilding = BuyBuildingCommand(gsm.player, building, this)
        /*val buyButton = TextButton("Buy: "+building.upgradeCost.toInt(), uiSkin)
        buyButton.isTransform = true
        buyButton.scaleBy(2.5f)*/
        val levelLabel = Label(building.level.toInt().toString(), fontStyle)
        val leftTable= VerticalGroup().pad(20f)
        leftTable.addActor(building.image)
        leftTable.addActor(levelLabel)

        val rightTable = VerticalGroup().pad(20f).space(230f).columnLeft()
        rightTable.addActor(Label(labelPrefix+building.value.toInt(), fontStyle))
        rightTable.addActor(buttonGrp)
        if(!buyBuilding.CanExecute()) {
//            buyButton.color = Color.RED
            rightTable.removeActor(buttonGrp)
            rightTable.removeActor(buttonGrpDown)
            rightTable.addActor(buttonGrpDisabled)
        } else {
            rightTable.removeActor(buttonGrpDisabled)
        }

        val group = HorizontalGroup().pad(20f)
        group.addActor(leftTable)
        group.addActor(rightTable)

        buttonGrp.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                rightTable.removeActor(buttonGrpDown)
                rightTable.addActor(buttonGrp)
                gsm.commandManager.Invoke(buyBuilding)
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                rightTable.removeActor(buttonGrp)
                rightTable.addActor(buttonGrpDown)
                return true
            }
        })

        return group
    }

    fun buildAllBuildings() {
            wholeGroup.add(buildingTemplate(gsm.player.passiveIncomeBuilding, BuildingType.INCOME, "$/sec: ")).space(20f)
            wholeGroup.row()
            wholeGroup.add(buildingTemplate(gsm.player.attackBuilding, BuildingType.ATTACK, "Attack:  ")).space(20f)
            wholeGroup.row()
            wholeGroup.add(buildingTemplate(gsm.player.defenseBuilding, BuildingType.DEFENSE, "Defense:  ")).space(20f)
            wholeGroup.row()
    }

    fun buildStatsTable() {
        var moneyPerSecStr = Label("Income per second: "+gsm.player.moneyPerSecond(), fontStyle)
        var attack = Label("Attack: "+gsm.player.attack(), fontStyle)
        var defense = Label("Defense: "+gsm.player.defense(), fontStyle)
        var statsTable = Table()
        statsTable.add(moneyStr)
        statsTable.row()
        statsTable.add(moneyPerSecStr)
        statsTable.row()
        statsTable.add(attack)
        statsTable.row()
        statsTable.add(defense)
//        wholeGroup.add(statsTable)
        wholeGroup.row().padTop(500f)
    }


    override fun update() {
        moneyStr.setText("Cash: "+ gsm.player.money)
        wholeGroup.clear()
        buildStatsTable()
        buildAllBuildings()
        updateTopBar()
    }

    override fun render(dt : Float) {
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            startTime = TimeUtils.nanoTime();
            playerController.addMoneySinceLastSynch()
        }
        batch.begin()
        batch.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        if (menuOpen) {
           menuActor.show()
        } else {
            menuActor.hide()
        }
        drawTopBar(batch)
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
