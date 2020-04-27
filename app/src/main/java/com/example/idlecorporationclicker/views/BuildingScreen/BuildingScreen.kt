package com.example.idlecorporationclicker.states.BuildingScreen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.models.audio.MusicPlayer
import com.example.idlecorporationclicker.controllers.commands.building.BuyBuildingCommand
import com.example.idlecorporationclicker.controllers.commands.player.PlayerController
import com.example.idlecorporationclicker.models.building.BuildingType
import com.example.idlecorporationclicker.models.building.IBuilding
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.SCREEN
import com.example.idlecorporationclicker.views.MenuActor
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
        menuActor = MenuActor(gsm)
        stage.addActor(menuActor.getActor())
        generateTopBar(stage, SCREEN.BuildingScreen, batch)
    }

    fun buildingTemplate(building : IBuilding, type: BuildingType, labelPrefix : String) : HorizontalGroup {
        var buyBuilding = BuyBuildingCommand(gsm.player, building, this)
        var buyButton = TextButton("Buy: "+building.upgradeCost.toInt(), uiSkin)
        buyButton.isTransform = true
        buyButton.scaleBy(2f)
        if(!buyBuilding.CanExecute()) {
            buyButton.color = Color.RED
        }
        var LevelLabel = Label(building.level.toInt().toString(), fontStyle )
        var leftTable= VerticalGroup().pad(5f)
        leftTable.addActor(building.image)
        leftTable.addActor(LevelLabel)

        var rightTable = VerticalGroup().pad(5f)
        rightTable.addActor(Label(labelPrefix+building.value.toInt(), fontStyle))
        rightTable.addActor(buyButton)

        var group = HorizontalGroup().pad(5f)
        group.addActor(leftTable)
        group.addActor(rightTable)

        buyButton.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                gsm.commandManager.Invoke(buyBuilding)
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        return group
    }

    fun buildAllBuildings() {
            wholeGroup.add(buildingTemplate(gsm.player.passiveIncomeBuilding, BuildingType.INCOME, "$ "))
            wholeGroup.row()
            wholeGroup.add(buildingTemplate(gsm.player.attackBuilding, BuildingType.ATTACK, "Attack power  "))
            wholeGroup.row()
            wholeGroup.add(buildingTemplate(gsm.player.defenseBuilding, BuildingType.DEFENSE, "Defense power "))
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
    }

    fun updateMoney() {
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            playerController.addMoneySinceLastSynch()
            startTime = TimeUtils.nanoTime();
        }
    }

    override fun render(dt : Float) {
        updateMoney()
        batch.begin()
        batch.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        updateTopBar(batch)
        if (menuOpen) {
           menuActor.show()
        } else {
            menuActor.hide()
        }
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
