package com.example.idlecorporationclicker.states

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import java.util.*

public class GameStateManager {

    private var states : Stack<State>

    constructor() {
        states = Stack<State>()
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