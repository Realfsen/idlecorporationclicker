package com.example.idlecorporationclicker.states

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.example.idlecorporationclicker.commands.CommandManager
import com.example.idlecorporationclicker.database.DatabaseController
import com.example.idlecorporationclicker.model.Player
import com.example.idlecorporationclicker.states.MainScreen.MainScreen
import com.example.idlecorporationclicker.states.attackscreen.AttackScreen
import java.util.*

class GameStateManager {

    private var states : Stack<State>
    private var screenHistory : Stack<SCREEN>
    var player : Player
    var commandManager : CommandManager
    private var generator : FreeTypeFontGenerator
    private var parameter : FreeTypeFontGenerator.FreeTypeFontParameter
    var fontStyle : Label.LabelStyle = Label.LabelStyle()


    constructor() {
        states = Stack<State>()
        screenHistory = Stack<SCREEN>()
        player = Player()
        DatabaseController.initiateLocalPlayer(player)
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

    fun push(state: State) {
        states.push(state)
    }

    fun pop() : State{
        return states.pop()
    }

    fun set(state: State) {
        states.pop()
        states.push(state)

    }

    fun peek() : State {
        return states.peek()
    }

    fun update(dt : Float) {
        states.peek().update(dt);
    }

    fun render(dt : Float) {
        states.peek().render(dt)
    }

    fun dispose() {
        states.peek().dispose()
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