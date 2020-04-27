package com.example.idlecorporationclicker.views.actors

import android.graphics.fonts.FontStyle
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.example.idlecorporationclicker.Launcher
import com.example.idlecorporationclicker.MainActivity
import com.example.idlecorporationclicker.models.audio.MusicPlayer
import com.example.idlecorporationclicker.models.audio.MusicPlayer.getMusicButtonTable
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.models.database.Database

class MenuActor(gsm : GameStateManager) {

    private var musicButton: MusicPlayer
    private var insideTable: Table
    private var menuTitle: Label
    private var generator : FreeTypeFontGenerator
    private var parameter : FreeTypeFontGenerator.FreeTypeFontParameter
    var fontStyle : Label.LabelStyle = Label.LabelStyle()
    private var table: Table
    private var gui: Texture
    private var menuBG: TextureRegionDrawable
    private var texture : TextureRegion
    private val screenHeight = Gdx.graphics.height.toFloat()
    private val screenWidth = Gdx.graphics.width.toFloat()
    private val buttonGfx : Image
    private val logOutBtn : Group

    init {

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

        musicButton = MusicPlayer
        val signika : BitmapFont = generator.generateFont(parameter)
        generator.dispose()
        fontStyle.font = signika

        gui = Texture(Gdx.files.internal("freegui/png/Window.png"))
        val buttons = Texture(Gdx.files.internal("freegui/png/Button.png"))
        val logOutTxt = Label("Log Out", fontStyle)
        buttonGfx = Image(TextureRegion(buttons, 625, 430, 430, 195))
        logOutBtn = Group()
        logOutBtn.addActor(buttonGfx)
        logOutBtn.addActor(logOutTxt)
        logOutTxt.setPosition(90f,75f)

        logOutBtn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                Database.forceMoneySync()
                gsm.androidApplication.signOut()
            }

            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        texture = TextureRegion(gui, 600, 1120, 455, 640)
        menuBG = TextureRegionDrawable(texture)
        menuTitle = Label("MENU", fontStyle)
        table = Table()
        insideTable = Table()
        generateTable()
    }

    fun generateTable() {
        table.setBackground(menuBG)
        table.width = screenWidth
        table.height = screenHeight/2
        table.setPosition(0f, screenHeight/3.5f)
        table.add(menuTitle).top().align(Align.top)
        table.row()
        //insideTable.top()
        //insideTable.row().padTop(10f)
        table.add(getMusicButtonTable()).top()
        //table.add(insideTable)
        table.row()
        table.add(logOutBtn).width(buttonGfx.width).height(buttonGfx.height).top()
        hide()
    }

    fun getActor() : Actor {
        return table
    }
    fun show() {
       table.isVisible = true
    }

    fun hide() {
       table.isVisible = false
    }

}