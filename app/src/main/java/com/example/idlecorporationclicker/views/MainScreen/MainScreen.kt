package com.example.idlecorporationclicker.views.MainScreen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import com.example.idlecorporationclicker.models.audio.MusicManager
import com.example.idlecorporationclicker.views.BuildingScreen.BuildingScreen
import com.example.idlecorporationclicker.views.GameStateManager
import com.example.idlecorporationclicker.views.SCREEN
import com.example.idlecorporationclicker.views.ScreenTemplate
import com.example.idlecorporationclicker.views.AttackScreen.AttackScreen


class MainScreen(override var game: Game, override var gsm: GameStateManager) : ScreenTemplate(gsm, game) {

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
    private var cookieManager : CookieClickerManager
    private var gui: Texture
    private var topBar : TextureRegion
    private var cashGfx : TextureRegion
    private var cashSymbol : TextureRegion
    private var shieldSymbol : TextureRegion
    private var starSymbol : TextureRegion
    private var numberBar : TextureRegion


    init {
        background = Texture("backgrounds/1x/background-basemdpi.png")
        cookie = Image(Texture("cookie/1x/cookiemdpi.png"))
        attackBuilding = Image(Texture("buildings/attack/base/1x/basemdpi.png"))
        incomeBuilding = Image(Texture("buildings/income/base/1x/basemdpi.png"))
        buildingTable = Table()
        clickerTable = Table()
        statsTable = Table()
        startTime = TimeUtils.nanoTime()
        cookieManager = CookieClickerManager()
        gui = Texture(Gdx.files.internal("freegui/png/Window.png"))
        topBar = TextureRegion(gui, 3540, 2845, 430, 140)
        cashGfx = TextureRegion(gui, 2890, 4100, 285, 115)
        cashSymbol = TextureRegion(gui, 1985, 4810, 95, 145)
        shieldSymbol = TextureRegion(gui, 1680, 4810, 110, 145)
        starSymbol = TextureRegion(gui, 1830, 4810, 135, 130)
        numberBar = TextureRegion(gui, 3699, 4294, 210, 58)

        var incomeStr: Label = Label("Cash", gsm.fontStyle)
        var attackStr: Label = Label("Attack", gsm.fontStyle)
        var moneyPerSecStr: Label = Label(""+gsm.player.moneyPerSecond()+" /s", gsm.fontStyle)
        moneyStr = Label(gsm.player.money.toString(), gsm.fontStyle)
        attack = Label(""+gsm.player.attack(), gsm.fontStyle)
        defense = Label(""+gsm.player.defense(), gsm.fontStyle)

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
                moneyStr.setText(gsm.player.money.toString())
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
//        statsTable.row()
//        statsTable.row()
        statsTable.setFillParent(true)
        statsTable.top()


        stage.addActor(MusicManager.getMusicButtonTable())
        stage.addActor(buildingTable)
        stage.addActor(clickerTable)
        stage.addActor(statsTable)
    }

    fun updateMoney() {
        gsm.player.addMoneySinceLastSynched()
    }

    fun drawTopBar() {
//        batch.draw(topBar, 0f, screenHeight-screenHeight/6, screenWidth, screenHeight/6)
//        /* CASH */
//        batch.draw(numberBar, 100f, screenHeight-screenHeight/13.5f, screenWidth/2.5f, screenHeight/20)
//        batch.draw(numberBar, 100f, screenHeight-screenHeight/7.5f, screenWidth/2.5f, screenHeight/20)
//        batch.draw(cashSymbol, 20f, screenHeight-cashSymbol.regionHeight*2.5f, screenWidth/6, screenHeight/6.5f)
//        /* DEFENSE */
//        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/13.5f, screenWidth/3f, screenHeight/20)
//        batch.draw(shieldSymbol, screenWidth/2f, screenHeight-shieldSymbol.regionHeight*1.25f, screenWidth/10f, screenHeight/16f)
//        /* ATTACK */
//        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/7.5f, screenWidth/3f, screenHeight/20)
//        batch.draw(starSymbol, screenWidth/2f, screenHeight-starSymbol.regionHeight*2.45f, screenWidth/10f, screenHeight/16f)
        batch.draw(topBar, 0f, screenHeight-screenHeight/6, screenWidth, screenHeight/6)
        /* CASH */
        batch.draw(numberBar, 100f, screenHeight-screenHeight/13.5f, screenWidth/2.5f, screenHeight/20)
        batch.draw(numberBar, 100f, screenHeight-screenHeight/7.5f, screenWidth/2.5f, screenHeight/20)
        batch.draw(cashSymbol, 20f, screenHeight-cashSymbol.regionHeight*2.0f, screenWidth/6, screenHeight/6.5f)
        /* DEFENSE */
        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/13.5f, screenWidth/3f, screenHeight/20)
        batch.draw(shieldSymbol, screenWidth/2f, screenHeight-shieldSymbol.regionHeight*1.0f, screenWidth/10f, screenHeight/16f)
        /* ATTACK */
        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/7.5f, screenWidth/3f, screenHeight/20)
        batch.draw(starSymbol, screenWidth/2f, screenHeight-starSymbol.regionHeight*2.0f, screenWidth/10f, screenHeight/16f)
    }



    override fun update() {
        moneyStr.setText(gsm.player.money.toString())
    }

    override fun render(dt : Float) {
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            updateMoney()
            startTime = TimeUtils.nanoTime();
            update()
        }

        batch.begin()
        batch.draw(background, 0f, 0f, screenWidth, screenHeight)
        drawTopBar()
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
//        generator.dispose()
    }
}