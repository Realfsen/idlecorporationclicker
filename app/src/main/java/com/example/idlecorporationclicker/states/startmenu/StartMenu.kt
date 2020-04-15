package com.example.idlecorporationclicker.states.startmenu

import android.graphics.fonts.Font
import com.badlogic.gdx.Gdx
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.MainActivity
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.MainScreen.MainScreen
import com.example.idlecorporationclicker.states.State

public class StartMenu(override var gsm: GameStateManager) : State(gsm) {
    private var background : Texture
    private var logo : Texture
    private var logoOffset : Float
    private var logoWidth: Float
    private var logoHeight: Float
    private var stage : Stage
    private var textField : TextField
    private var uiSkin : Skin
    private var loginBtn : TextButton
    private var loginTable : Table

    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-basemdpi.png"))
        logo = Texture(Gdx.files.internal("logo/2x/logoxhdpi.png"))
        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        logoOffset = 0.8f
        logoWidth = logo.width.toFloat()*logoOffset
        logoHeight = logo.height.toFloat()*logoOffset

        var tableWidth : Float = Gdx.graphics.width.toFloat()/2
        var tableHeight : Float = 200f

        textField = TextField("Insert name here", uiSkin)
        textField.setSize(tableWidth, 180f)
        loginBtn = TextButton("Set username", uiSkin)
        uiSkin.getFont("default-font").getData().setScale(5f)

        loginBtn.setSize(tableWidth, 180f)
        loginBtn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                gsm.set(MainScreen(gsm))
            }
        })

        loginTable = Table()
        loginTable.add(textField).width(tableWidth+200).height(tableHeight)
        loginTable.row()
        loginTable.add(loginBtn).width(Gdx.graphics.width.toFloat()/2).height(tableHeight)
        loginTable.setFillParent(true);



        stage = Stage(ScreenViewport(cam))
        stage.addActor(loginTable)
        stage.setKeyboardFocus(textField)
        Gdx.input.setInputProcessor(stage)
    }

    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        sb.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        sb.draw(logo, (Gdx.graphics.width.toFloat()/2) - (logoWidth/2), Gdx.graphics.height.toFloat()/1.8f,logoWidth, logoHeight)
        sb.end()
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }
}