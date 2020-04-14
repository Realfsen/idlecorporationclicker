package com.example.idlecorporationclicker.states

import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector3

abstract class State (gsm : GameStateManager) {
    private var cam : OrthographicCamera
    private var mouse : Vector3
    abstract var gsm : GameStateManager

    init {
        this.gsm = gsm;
        cam  = OrthographicCamera()
        mouse = Vector3();

    }

    public abstract fun handleInput()
    public abstract fun update(dt : Float);
    public abstract  fun render(sb : SpriteBatch)

}