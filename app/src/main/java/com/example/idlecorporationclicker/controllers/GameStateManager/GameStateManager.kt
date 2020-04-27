package com.example.idlecorporationclicker.states

import com.badlogic.gdx.Game
import com.badlogic.gdx.backends.android.AndroidApplication
import com.example.idlecorporationclicker.Launcher
import com.example.idlecorporationclicker.controllers.CommandManager
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
    var androidApplication : Launcher


    public var showTutorial : Boolean

    constructor(app : Launcher) {
        showTutorial = true;
        screenTemplates = Stack<ScreenTemplate>()
        screenHistory = Stack<SCREEN>()
        player = Player()
        Database.initiateLocalPlayer(player)
        commandManager =
            CommandManager()
        androidApplication = app;
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