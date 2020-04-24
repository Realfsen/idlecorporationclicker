package com.example.idlecorporationclicker.states

import com.badlogic.gdx.Game
import com.example.idlecorporationclicker.database.DatabaseController
import com.example.idlecorporationclicker.model.Player
import com.example.idlecorporationclicker.states.MainScreen.MainScreen
import com.example.idlecorporationclicker.states.attackscreen.AttackScreen
import java.util.*

class GameStateManager {

    private var states : Stack<State>
    private var screenHistory : Stack<SCREEN>
    var player : Player


    constructor() {
        states = Stack<State>()
        screenHistory = Stack<SCREEN>()
        player = Player()
        DatabaseController.initiateLocalPlayer(player)
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