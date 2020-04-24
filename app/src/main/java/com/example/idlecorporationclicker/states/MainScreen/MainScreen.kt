package com.example.idlecorporationclicker.states.MainScreen

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import com.example.idlecorporationclicker.audio.MusicManager
import com.example.idlecorporationclicker.states.BuildingScreen.BuildingScreen
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.SCREEN
import com.example.idlecorporationclicker.states.State
import com.example.idlecorporationclicker.states.attackscreen.AttackScreen


class MainScreen(override var game: Game, override var gsm: GameStateManager) : State(gsm, game) {

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
    private var numberBar : TextureRegion
    private var generator : FreeTypeFontGenerator
    private var parameter : FreeTypeFontParameter


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
        numberBar = TextureRegion(gui, 3699, 4294, 210, 58)

        generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/signika/Signika-Bold.ttf"))
        parameter = FreeTypeFontParameter()

        val signika : BitmapFont = generator.generateFont(parameter)
        generator.dispose()

        val newLabelStyle : Label.LabelStyle = Label.LabelStyle()
        newLabelStyle.font = signika

        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(4f)

        var incomeStr: Label = Label("Cash", newLabelStyle)
        var attackStr: Label = Label("Attack", newLabelStyle)
        var moneyPerSecStr: Label = Label(""+gsm.player.moneyPerSecond()+" per second", newLabelStyle)
        moneyStr = Label(gsm.player.money.toString(), newLabelStyle)
        attack = Label("Attack: "+gsm.player.attack(), newLabelStyle)
        defense = Label("Defense: "+gsm.player.defense(), newLabelStyle)

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

        statsTable.add(moneyStr)
//        statsTable.row()
        statsTable.add(moneyPerSecStr)
        statsTable.row()
        statsTable.add(attack)
//        statsTable.row()
        statsTable.add(defense)
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
        batch.draw(topBar, 0f, screenHeight-screenHeight/6, screenWidth, screenHeight/6)
//        batch.draw(cashGfx, 25f, screenHeight-cashGfx.regionHeight*2.5f, screenWidth/2.5f, screenHeight/12)
        batch.draw(numberBar, 100f, screenHeight-screenHeight/13.5f, screenWidth/2.25f, screenHeight/20)
        batch.draw(numberBar, 100f, screenHeight-screenHeight/7.5f, screenWidth/2.25f, screenHeight/20)
        batch.draw(cashSymbol, 20f, screenHeight-cashGfx.regionHeight*3f, screenWidth/6, screenHeight/6.5f)
    }


    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    override fun render(dt : Float) {
        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            updateMoney()
            startTime = TimeUtils.nanoTime();
            moneyStr.setText(gsm.player.money.toString())
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
        generator.dispose()
    }
}