package com.example.idlecorporationclicker.states

import com.badlogic.gdx.graphics.g2d.SpriteBatch
import java.util.*

public class GameStateManager {

    private var states : Stack<State>

    constructor() {
        states = Stack<State>()
    }

    public fun push(state: State) {
        states.push(state)
    }

    public fun pop(state: State) {
        states.pop()
    }

    public fun set(state: State) {
        states.pop()
        states.push(state)
    }

    public fun update(dt : Float) {
        states.peek().update(dt);
    }

    public fun render(sb : SpriteBatch) {
        states.peek().render(sb)
    }
}