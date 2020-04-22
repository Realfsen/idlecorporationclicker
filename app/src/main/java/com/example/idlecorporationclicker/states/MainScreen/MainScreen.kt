package com.example.idlecorporationclicker.states.MainScreen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.states.BuildingScreen.BuildingScreen
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.SCREEN
import com.example.idlecorporationclicker.states.State
import com.example.idlecorporationclicker.states.attackscreen.AttackScreen
import java.util.*

class MainScreen(override var game: Game, override var gsm: GameStateManager) : State(gsm, game) {



    private var background : Texture
    private var cookie: Image
    private var attackBuilding: Image
    private var incomeBuilding: Image
    private var buildingTable : Table
    private var clickerTable : Table
    private var statsTable : Table
    private var batch : SpriteBatch
    private var stage: Stage
    private var startTime : Long
    private var moneyStr : Label
    private var attack : Label
    private var defense: Label


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-basemdpi.png"))
        cookie = Image(Texture(Gdx.files.internal("cookie/1x/cookiemdpi.png")))
        attackBuilding = Image(Texture(Gdx.files.internal("buildings/attack/base/1x/basemdpi.png")))
        incomeBuilding = Image(Texture(Gdx.files.internal("buildings/income/base/1x/basemdpi.png")))
        buildingTable = Table()
        clickerTable = Table()
        statsTable = Table()
        startTime = TimeUtils.nanoTime()

        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(5f)

        var incomeStr: Label = Label("Income", uiSkin)
        var attackStr: Label = Label("Attack", uiSkin)
        moneyStr = Label("Income: "+gsm.player.income, uiSkin)
        var moneyPerSecStr: Label = Label("Income per second: "+gsm.player.moneyPerSecond(), uiSkin)
        attack = Label("Attack: "+gsm.player.attack(), uiSkin)
        defense = Label("Defense: "+gsm.player.defense(), uiSkin)

        stage = Stage()
        batch = SpriteBatch()
        attackBuilding.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                game.setScreen(AttackScreen(game, gsm))
                gsm.pushHistory(SCREEN.MainScreen)
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        incomeBuilding.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                game.setScreen(BuildingScreen(game, gsm))
                gsm.pushHistory(SCREEN.MainScreen)
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        cookie.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                gsm.player.addClickMoney()
                moneyStr.setText("Income: "+ gsm.player.income)
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        buildingTable.add(incomeBuilding).expandX()
        buildingTable.add(attackBuilding).expandX()
        buildingTable.bottom()
        buildingTable.setFillParent(true)
        buildingTable.row()

        buildingTable.add(incomeStr)
        buildingTable.add(attackStr)

        clickerTable.add(cookie)
        clickerTable.setFillParent(true)

        statsTable.add(moneyStr)
        statsTable.row()
        statsTable.add(moneyPerSecStr)
        statsTable.row()
        statsTable.add(attack)
        statsTable.row()
        statsTable.add(defense)
        statsTable.setFillParent(true)
        statsTable.top()

        stage.addActor(buildingTable)
        stage.addActor(clickerTable)
        stage.addActor(statsTable)


    }

    fun updateMoney() {
        gsm.player.addMoneySinceLastSynched()
    }


    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    override fun render(dt : Float) {
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            updateMoney()
            startTime = TimeUtils.nanoTime();
            moneyStr.setText("Income: "+ gsm.player.income)
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