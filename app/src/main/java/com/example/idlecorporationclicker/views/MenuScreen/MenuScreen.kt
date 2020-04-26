package com.example.idlecorporationclicker.views.MenuScreen

import android.util.Log
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Stage
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.views.ScreenTemplate

class MenuScreen(
    override var game: Game,
    override var gsm: GameStateManager
) : ScreenTemplate(gsm, game) {

    private var batch : SpriteBatch
    private var stage : Stage
    private var gui : Texture
    private var background : TextureRegion

    init {
        batch = SpriteBatch()
        stage = Stage()
        gui = Texture(Gdx.files.internal("freegui/png/Window.png"))
        background = TextureRegion(gui, 600f, 1120f, 455f, 640f)
    }

    override fun update() {
    }

    override fun render(delta: Float) {
        batch.begin()
        batch.draw(background, 500f, 500f, 500f, 500f)
        batch.end()
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }


    override fun hide() {
    }

    override fun show() {
        Gdx.input.setInputProcessor(stage)
    }

    override fun pause() {
    }

    override fun resume() {
    }

    override fun resize(width: Int, height: Int) {
    }

    override fun dispose() {
        batch.dispose()
        stage.dispose()
    }
}
