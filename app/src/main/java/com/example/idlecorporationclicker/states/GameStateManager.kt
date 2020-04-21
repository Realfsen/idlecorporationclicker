package com.example.idlecorporationclicker.states

import com.badlogic.gdx.Game
import com.example.idlecorporationclicker.model.Player
import com.example.idlecorporationclicker.states.BuildingScreen.BuildingScreen
import com.example.idlecorporationclicker.states.MainScreen.MainScreen
import com.example.idlecorporationclicker.states.attackscreen.AttackScreen
import com.example.idlecorporationclicker.states.playerlist.PlayerList
import java.util.*

public class GameStateManager {

    private var states : Stack<State>
    private var screenHistory : Stack<SCREEN>
    private var player : Player


    constructor() {
        states = Stack<State>()
        screenHistory = Stack<SCREEN>()
        player = Player()
    }

    public fun pushHistory(hist : SCREEN) {
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

    public fun push(state: State) {
        states.push(state)
    }

    public fun pop() : State{
        return states.pop()
    }

    public fun set(state: State) {
        states.pop()
        states.push(state)

    }

    public fun peek() : State {
        return states.peek()
    }

    public fun update(dt : Float) {
        states.peek().update(dt);
    }

    public fun render(dt : Float) {
        states.peek().render(dt)
    }

    public fun dispose() {
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