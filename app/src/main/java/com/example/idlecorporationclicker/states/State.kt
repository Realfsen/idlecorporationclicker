package com.example.idlecorporationclicker.states

import com.badlogic.gdx.Game
import com.badlogic.gdx.Screen
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage

abstract class State (gsm: GameStateManager, game : Game) : Screen {
    public var cam : OrthographicCamera
    private var mouse : Vector3
    abstract var game: Game
    abstract var gsm: GameStateManager

    init {
        this.gsm = gsm
        this.game = game
        cam  = OrthographicCamera()
        mouse = Vector3();
        cam.setToOrtho(false, 480f, 800f)
    }

    public abstract fun handleInput()
    public abstract fun update(dt : Float);

}