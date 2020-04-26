package com.example.idlecorporationclicker.views

import android.util.Log
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.SCREEN
import com.example.idlecorporationclicker.views.MenuScreen.MenuScreen

abstract class ScreenTemplate (gsm: GameStateManager, game : Game) : Screen {
    public var cam : OrthographicCamera
    private var mouse : Vector3
    abstract var game: Game
    abstract var gsm: GameStateManager

    private var generator : FreeTypeFontGenerator
    private var parameter : FreeTypeFontGenerator.FreeTypeFontParameter
    var fontStyle : Label.LabelStyle = Label.LabelStyle()

    private val screenHeight = Gdx.graphics.height.toFloat()
    private val screenWidth = Gdx.graphics.width.toFloat()
    private var gui: Texture
    public var topBar : TextureRegion
    private var cashSymbol : TextureRegion
    private var shieldSymbol : TextureRegion
    private var starSymbol : TextureRegion
    private var numberBar : TextureRegion
    private var cash : Label
    private var cashSec: Label
    private var defense: Label
    private var attack: Label
    private var buttons: Texture
    private var menuButton: Image
    var menuBG : TextureRegion
    var menuOpen : Boolean

    init {
        this.gsm = gsm
        this.game = game
        cam  = OrthographicCamera()
        mouse = Vector3();
        cam.setToOrtho(false, 480f, 800f)

        /* START FONT GENERATOR */
        generator = FreeTypeFontGenerator(Gdx.files.internal("fonts/signika/Signika-Bold.ttf"))
        parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        /* FONT PARAMETERS */
        parameter.size = 60
        parameter.borderWidth = 7f
        parameter.borderColor = Color.ORANGE;
        parameter.shadowColor = Color(0f, 0f, 0f, 0.75f)
        parameter.shadowOffsetX = 1
        parameter.shadowOffsetY = 1

        val signika : BitmapFont = generator.generateFont(parameter)
        generator.dispose()
        fontStyle.font = signika
        /* END FONT GENERATOR */

        /* BEGIN TOP BAR */
        gui = Texture(Gdx.files.internal("freegui/png/Window.png"))
        topBar = TextureRegion(gui, 3540, 2845, 430, 140)
        cashSymbol = TextureRegion(gui, 1985, 4810, 95, 145)
        shieldSymbol = TextureRegion(gui, 1680, 4810, 110, 145)
        starSymbol = TextureRegion(gui, 1830, 4810, 135, 130)
        numberBar = TextureRegion(gui, 3699, 4294, 210, 58)
        cash = Label(gsm.player.money.toString(), fontStyle)
        cashSec = Label(gsm.player.moneyPerSecond().toString(), fontStyle)
        defense = Label(gsm.player.defense().toString(), fontStyle)
        attack = Label(gsm.player.attack().toString(), fontStyle)
        buttons = Texture(Gdx.files.internal("freegui/png/Button.png"))
        menuButton = Image(TextureRegion(buttons, 405, 1845, 180, 190))
        /* END TOP BAR */

        /* MENU */
//        menuBG = TextureRegion(gui, 600f, 1120f, 455f, 640f)
        menuBG = TextureRegion(gui, 600, 1120, 455, 640)
        menuOpen = false
    }

    abstract fun update()

    fun generateTopBar(stage: Stage, screen: SCREEN, batch: SpriteBatch) {
        /* MENU BUTTON */
        menuButton.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                openMenu()
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })
        menuButton.setPosition(screenWidth-170f, screenHeight-177.5f)
        menuButton.setScale(0.7f)
        stage.addActor(menuButton)
        /* CASH */
        cash.setPosition(210f, screenHeight-150f)
        stage.addActor(cash)
        cashSec.setPosition(210f, screenHeight-285f)
        stage.addActor(cashSec)
        /* DEFENSE */
        defense.setPosition(680f, screenHeight-150f)
        stage.addActor(defense)
        /* ATTACK */
        attack.setPosition(680f, screenHeight-285f)
        stage.addActor(attack)
    }

    fun updateTopBar(batch: SpriteBatch) {
        batch.draw(topBar, 0f, screenHeight-screenHeight/6, screenWidth, screenHeight/6)
        /* CASH */
        batch.draw(numberBar, 100f, screenHeight-screenHeight/13.5f, screenWidth/2.5f, screenHeight/20)
        batch.draw(numberBar, 100f, screenHeight-screenHeight/7.5f, screenWidth/2.5f, screenHeight/20)
        batch.draw(cashSymbol, 20f, screenHeight-350f, screenWidth/6, screenHeight/6.5f)
        cash.setText(gsm.player.money.toString())
        cashSec.setText(gsm.player.moneyPerSecond().toInt().toString()+" /s")
        /* DEFENSE */
        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/13.5f, screenWidth/3f, screenHeight/20)
        batch.draw(shieldSymbol, screenWidth/2f, screenHeight-190f, screenWidth/10f, screenHeight/16f)
        defense.setText(gsm.player.defense().toInt().toString())
        /* ATTACK */
        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/7.5f, screenWidth/3f, screenHeight/20)
        batch.draw(starSymbol, screenWidth/2f, screenHeight-315f, screenWidth/10f, screenHeight/18f)
        attack.setText(gsm.player.attack().toInt().toString())
    }

    fun openMenu() {
        Log.d("test", "openMenu")
        menuOpen = !menuOpen
    }

}