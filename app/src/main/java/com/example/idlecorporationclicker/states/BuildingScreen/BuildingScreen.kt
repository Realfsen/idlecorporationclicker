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
import com.example.idlecorporationclicker.audio.MusicManager
import com.example.idlecorporationclicker.commands.BuyBuildingCommand
import com.example.idlecorporationclicker.commands.SellBuildingCommand
import com.example.idlecorporationclicker.model.BuildingType
import com.example.idlecorporationclicker.model.IBuilding
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.State

class BuildingScreen(override var game: Game, override var gsm: GameStateManager) : State(gsm, game) {


    private var startTime: Long
    private var moneyStr: Label
    private var background : Texture
    private var wholeGroup : Table
    private var batch : SpriteBatch
    private var stage: Stage
    private var uiSkin : Skin


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-basemdpi.png"))
        stage = Stage(ScreenViewport(cam))
        batch = SpriteBatch()
        wholeGroup = Table()
        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)
        moneyStr = Label("Income: "+gsm.player.money, uiSkin)
        startTime = TimeUtils.nanoTime()



        buildStatsTable()
        buildAllBuildings()
        wholeGroup.setFillParent(true)
        wholeGroup.top()
        stage.addActor(wholeGroup)
        stage.addActor(MusicManager.getMusicButtonTable())
    }

    fun buildingTemplate(building : IBuilding, type: BuildingType, labelPrefix : String) : HorizontalGroup {
        var buyBuilding = BuyBuildingCommand(gsm.player, building)
        var BuyButton = TextButton("Buy: "+building.upgradeCost.toInt(), uiSkin)
        if(!buyBuilding.CanExecute()) {
            BuyButton.color = Color.RED
        }
        var LevelLabel = Label(building.level.toInt().toString(), uiSkin )
        var leftTable= VerticalGroup().pad(5f)
        leftTable.addActor(building.image)
        leftTable.addActor(LevelLabel)

        var rightTable = VerticalGroup().pad(5f)
        rightTable.addActor(Label(labelPrefix+building.value.toInt(), uiSkin))
        rightTable.addActor(BuyButton)

        var group = HorizontalGroup().pad(5f)
        group.addActor(leftTable)
        group.addActor(rightTable)

        BuyButton.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                gsm.commandManager.Invoke(buyBuilding)
                wholeGroup.clear()
                buildStatsTable()
                buildAllBuildings()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        return group
    }

    fun buildAllBuildings() {
//        gsm.player.passiveIncomeBuildings.forEach( {
//            wholeGroup.add(buildingTemplate(it, BuildingType.INCOME, "$ "))
//            wholeGroup.row()
//        })
//        gsm.player.attackBuildings.forEach( {
//            wholeGroup.add(buildingTemplate(it, BuildingType.ATTACK, "Attack power  "))
//            wholeGroup.row()
//        })
//        gsm.player.defenseBuildings.forEach( {
//            wholeGroup.add(buildingTemplate(it, BuildingType.DEFENSE, "Defense power "))
//            wholeGroup.row()
//        })

            wholeGroup.add(buildingTemplate(gsm.player.passiveIncomeBuilding, BuildingType.INCOME, "$ "))
            wholeGroup.row()
            wholeGroup.add(buildingTemplate(gsm.player.attackBuilding, BuildingType.ATTACK, "Attack power  "))
            wholeGroup.row()
            wholeGroup.add(buildingTemplate(gsm.player.defenseBuilding, BuildingType.DEFENSE, "Defense power "))
            wholeGroup.row()
    }

    fun buildStatsTable() {
        var moneyPerSecStr = Label("Income per second: "+gsm.player.moneyPerSecond(), uiSkin)
        var attack = Label("Attack: "+gsm.player.attack(), uiSkin)
        var defense = Label("Defense: "+gsm.player.defense(), uiSkin)
        var statsTable = Table()
        statsTable.add(moneyStr)
        statsTable.row()
        statsTable.add(moneyPerSecStr)
        statsTable.row()
        statsTable.add(attack)
        statsTable.row()
        statsTable.add(defense)
        wholeGroup.add(statsTable)
        wholeGroup.row().padTop(40f)
    }

    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    fun updateMoney() {
        gsm.player.addMoneySinceLastSynched()
    }

    override fun render(dt : Float) {
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            updateMoney()
            startTime = TimeUtils.nanoTime();
            moneyStr.setText("Income: "+ gsm.player.money)
            wholeGroup.clear()
            buildStatsTable()
            buildAllBuildings()
        }
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
