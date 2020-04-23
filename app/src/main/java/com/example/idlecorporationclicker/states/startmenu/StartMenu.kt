package com.example.idlecorporationclicker.states.startmenu

import android.graphics.fonts.Font
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.MainActivity
import com.example.idlecorporationclicker.audio.MusicManager
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.MainScreen.MainScreen
import com.example.idlecorporationclicker.states.State

public class StartMenu(override var game: Game, override var gsm: GameStateManager) : State(gsm, game) {

    private val screenHeight = Gdx.graphics.height.toFloat()
    private val screenWidth = Gdx.graphics.width.toFloat()
    private var stage: Stage
    private var background : Texture
    private var logo : Texture
    private var logoOffset : Float
    private var logoWidth: Float
    private var logoHeight: Float
    private var textField : TextField? = null
    private var uiSkin : Skin
    private var loginBtn : TextButton
    private var loginTable : Table
    private var batch : SpriteBatch

    init {
        background = Texture("backgrounds/1x/background-basemdpi.png")
        logo = Texture("logo/2x/logoxhdpi.png")
        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        logoOffset = 0.8f
        logoWidth = logo.width.toFloat()*logoOffset
        logoHeight = logo.height.toFloat()*logoOffset




        var tableWidth : Float = screenWidth/2f
        var tableHeight : Float = 200f

        /**
         * Henter navnet fra innloggingen, så trenger ikke sette eget brukernavn
         * (Fjern denne kommentaren før innlevering)
         */
//        textField = TextField("Insert name here", uiSkin)
//        textField.setSize(tableWidth, 180f)
        loginBtn = TextButton("Start", uiSkin)
        uiSkin.getFont("default-font").getData().setScale(5f)

        loginBtn.setSize(tableWidth, 180f)
        loginBtn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                game.setScreen(MainScreen(game, gsm))
            }
        })

        loginTable = Table()
        loginTable.add(textField).width(tableWidth+200).height(tableHeight)
        loginTable.row()
        loginTable.add(loginBtn).width(screenWidth/2f).height(tableHeight)
//        loginTable.add(textField).width(tableWidth+200).height(tableHeight)
//        loginTable.row()
        loginTable.add(loginBtn).width(Gdx.graphics.width.toFloat()/2).height(tableHeight)
        loginTable.setFillParent(true);
        stage = Stage(ScreenViewport(cam))
        batch = SpriteBatch()
        stage.addActor(loginTable)
        stage.addActor(MusicManager.getMusicButtonTable())
    }

    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    override fun render(dt: Float) {
        batch.begin()
        batch.draw(background, 0f, 0f, screenWidth, screenHeight)
        batch.draw(logo, (screenWidth/2) - (logoWidth/2f), screenHeight/1.9f,logoWidth, logoHeight)
//        batch.draw(musicBtn, 10f, screenHeight - screenHeight/10f, 120f, 120f)
        batch.end()
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }


    override fun dispose() {
        batch.dispose()
        stage.dispose()
    }

    override fun hide() {

    }

    override fun show() {
        Gdx.input.setInputProcessor(stage)
//        stage.setKeyboardFocus(textField)
    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }
}