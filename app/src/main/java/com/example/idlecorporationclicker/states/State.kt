package com.example.idlecorporationclicker.states

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.scenes.scene2d.Stage

abstract class State (gsm : GameStateManager) {
    public var cam : OrthographicCamera
    private var mouse : Vector3
    abstract var gsm : GameStateManager

    init {
        this.gsm = gsm;
        cam  = OrthographicCamera()
        mouse = Vector3();
        cam.setToOrtho(false, 480f, 800f)

    }

    public abstract fun handleInput()
    public abstract fun update(dt : Float);
    public abstract  fun render(sb : SpriteBatch)

}