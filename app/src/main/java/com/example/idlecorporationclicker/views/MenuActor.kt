package com.example.idlecorporationclicker.views

import android.graphics.fonts.FontStyle
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.example.idlecorporationclicker.models.audio.MusicPlayer
import com.example.idlecorporationclicker.models.audio.MusicPlayer.getMusicButtonTable
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.models.audio.MusicPlayer.musicBtn

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
        table.add(menuTitle).top().padTop(50f).align(Align.top)
        table.row()
        //insideTable.top()
        //insideTable.row().padTop(10f)
        table.add(getMusicButtonTable()).top()
        //table.add(insideTable)
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