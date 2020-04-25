package com.example.idlecorporationclicker.states

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.example.idlecorporationclicker.controllers.commands.CommandManager
import com.example.idlecorporationclicker.models.database.Database
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.states.MainScreen.MainScreen
import com.example.idlecorporationclicker.views.AttackScreen.AttackScreen
import com.example.idlecorporationclicker.views.ScreenTemplate
import java.util.*

class GameStateManager {

    private var screenTemplates : Stack<ScreenTemplate>
    private var screenHistory : Stack<SCREEN>
    var player : Player
    var commandManager : CommandManager
    private var generator : FreeTypeFontGenerator
    private var parameter : FreeTypeFontGenerator.FreeTypeFontParameter
    var fontStyle : Label.LabelStyle = Label.LabelStyle()

    private val screenHeight = Gdx.graphics.height.toFloat()
    private val screenWidth = Gdx.graphics.width.toFloat()
    private var gui: Texture
    private var topBar : TextureRegion
    private var cashSymbol : TextureRegion
    private var shieldSymbol : TextureRegion
    private var starSymbol : TextureRegion
    private var numberBar : TextureRegion
    private var cash : Label
    private var cashSec: Label
    private var defense: Label
    private var attack: Label


    constructor() {
        screenTemplates = Stack<ScreenTemplate>()
        screenHistory = Stack<SCREEN>()
        player = Player()
        Database.initiateLocalPlayer(player)
        commandManager = CommandManager()

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
        cash = Label(player.money.toString(), fontStyle)
        cashSec = Label(player.moneyPerSecond().toString(), fontStyle)
        defense = Label(player.defense().toString(), fontStyle)
        attack = Label(player.attack().toString(), fontStyle)
        /* END TOP BAR */
    }

    fun pushHistory(hist : SCREEN) {
        screenHistory.push(hist)
    }

    fun popHistory() : SCREEN {
       return screenHistory.pop()
    }

    fun setNewScreenFromStack(gsm : GameStateManager, game: Game){
        if(!screenHistory.isEmpty()) {
            var scrn = popHistory()
            when(scrn) {
                SCREEN.AttackScreen -> game.setScreen(AttackScreen(game, gsm))
                SCREEN.MainScreen -> game.setScreen(MainScreen(game, gsm))
            }
        }
    }

    fun drawTopBar(batch: SpriteBatch, stage: Stage) {
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
        batch.draw(cashSymbol, 20f, screenHeight-350f, screenWidth/6, screenHeight/6.5f)
        cash.setText(player.money.toString())
        cash.setPosition(210f, screenHeight-150f)
        stage.addActor(cash)
        cashSec.setText(player.moneyPerSecond().toInt().toString()+" /s")
        cashSec.setPosition(210f, screenHeight-285f)
        stage.addActor(cashSec)
        /* DEFENSE */
        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/13.5f, screenWidth/3f, screenHeight/20)
        batch.draw(shieldSymbol, screenWidth/2f, screenHeight-190f, screenWidth/10f, screenHeight/16f)
        defense.setText(player.defense().toInt().toString())
        defense.setPosition(680f, screenHeight-150f)
        stage.addActor(defense)
        /* ATTACK */
        batch.draw(numberBar, screenWidth/2f, screenHeight-screenHeight/7.5f, screenWidth/3f, screenHeight/20)
        batch.draw(starSymbol, screenWidth/2f, screenHeight-315f, screenWidth/10f, screenHeight/18f)
        attack.setText(player.attack().toInt().toString())
        attack.setPosition(680f, screenHeight-285f)
        stage.addActor(attack)
    }

    fun push(screenTemplate: ScreenTemplate) {
        screenTemplates.push(screenTemplate)
    }

    fun pop() : ScreenTemplate{
        return screenTemplates.pop()
    }

    fun set(screenTemplate: ScreenTemplate) {
        screenTemplates.pop()
        screenTemplates.push(screenTemplate)

    }

    fun peek() : ScreenTemplate {
        return screenTemplates.peek()
    }

    fun update(dt : Float) {
        screenTemplates.peek().update();
    }

    fun render(dt : Float) {
        screenTemplates.peek().render(dt)
    }

    fun dispose() {
        screenTemplates.peek().dispose()
    }
}

enum class SCREEN {
    None,
    StartMenu,
    MainScreen,
    AttackScreen,
    BuildingScreen,
    PlayerList
}