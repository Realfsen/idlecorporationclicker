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
import com.example.idlecorporationclicker.controllers.player.PlayerController
import com.example.idlecorporationclicker.views.actors.CookieClicker
import com.example.idlecorporationclicker.states.BuildingScreen.BuildingScreen
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.SCREEN
import com.example.idlecorporationclicker.views.AttackScreen.AttackScreen
import com.example.idlecorporationclicker.views.actors.MenuActor
import com.example.idlecorporationclicker.views.ScreenTemplate
import com.example.idlecorporationclicker.views.actors.Tutorial


class MainScreen(override var game: Game, override var gsm: GameStateManager) : ScreenTemplate(gsm, game) {

    private var tutorialBtn: TextButton
    private var uiSkin: Skin
    private val screenHeight = Gdx.graphics.height.toFloat()
    private val screenWidth = Gdx.graphics.width.toFloat()
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
    private var cookieManager : CookieClicker
    private var playerController : PlayerController
    private var tutorial : Tutorial

    private var menuTitle : Label
    private var menu : MenuActor

    init {
        tutorial = Tutorial(
            fontStyle,
            screenWidth,
            screenHeight
        )
        background = Texture("backgrounds/1x/background-basemdpi.png")
        cookie = Image(Texture("cookie/1x/cookiemdpi.png"))
        attackBuilding = Image(Texture("attacks/1x/dualstealmdpi.png"))
//        attackBuilding = Image(Texture("buildings/attack/base/1x/basemdpi.png"))
        incomeBuilding = Image(Texture("buildings/income/base/1x/basemdpi_buildings.png"))
        buildingTable = Table()
        clickerTable = Table()
        statsTable = Table()
        startTime = TimeUtils.nanoTime()
        cookieManager =
            CookieClicker()
        playerController = PlayerController(gsm.player, this)
        menuOpen = false
        menuTitle = Label("MENU", fontStyle)
        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        tutorialBtn = TextButton("Tutorial", uiSkin)

        menu = MenuActor(gsm)

        val incomeStr: Label = Label("Buildings", fontStyle)
        val attackStr: Label = Label("Attack", fontStyle)
        val moneyPerSecStr: Label = Label(""+gsm.player.moneyPerSecond()+" /s", fontStyle)
        moneyStr = Label(gsm.player.money.toString(), fontStyle)
        attack = Label(""+gsm.player.attack(), fontStyle)
        defense = Label(""+gsm.player.defense(), fontStyle)

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


        if(gsm.showTutorial) {
            gsm.showTutorial = false;
            uiSkin.getFont("default-font").getData().setScale(3f)
            tutorialBtn.setHeight(100f)
            tutorialBtn.setWidth(200f)
            tutorialBtn.setPosition(screenWidth/2-tutorialBtn.width/2, screenHeight/1.5f-tutorialBtn.height/2)
            stage.addActor(tutorialBtn)
            tutorialBtn.addListener(object : ClickListener() {
                override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                    tutorial.startTutorial(stage)
                    tutorialBtn.remove()
                }
                override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                    return true
                }
            })
        }
        cookie.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                playerController.addMoneyByClick()
                stage.addActor(cookieManager.getNextActor(x, y, screenHeight))
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

        statsTable.add(moneyStr).width(screenWidth/2.5f).padTop(55f)
        statsTable.add(defense).padTop(55f)
        statsTable.row()
        statsTable.add(moneyPerSecStr).width(screenWidth/2.5f).padTop(40f)
        statsTable.add(attack).padTop(40f)
        statsTable.setFillParent(true)
        statsTable.top()


        stage.addActor(buildingTable)
        stage.addActor(clickerTable)
        generateTopBar(stage)
        stage.addActor(menu.getActor())
    }

    fun updateMoney() {
        gsm.player.addMoneySinceLastSynched()
    }

    override fun update() {
        moneyStr.setText(gsm.player.money.toString())
        updateTopBar()
    }

    override fun render(dt : Float) {
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            startTime = TimeUtils.nanoTime();
            playerController.addMoneySinceLastSynch()
        }

        batch.begin()
        batch.draw(background, 0f, 0f, screenWidth, screenHeight)
        if (menuOpen) {
            cookie.isVisible = false
            menu.show()
        } else {
            menu.hide()
            cookie.isVisible = true
        }
        drawTopBar(batch)
        batch.end()
        stage.draw()
        stage.act(Gdx.graphics.deltaTime)
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
//        generator.dispose()
    }
}