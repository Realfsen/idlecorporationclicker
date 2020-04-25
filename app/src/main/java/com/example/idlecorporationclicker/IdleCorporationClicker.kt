package com.example.idlecorporationclicker
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.example.idlecorporationclicker.views.GameStateManager
import com.example.idlecorporationclicker.views.StartMenu.StartMenu
import com.example.idlecorporationclicker.models.audio.MusicPlayer


class IdleCorporationClicker : Game() {
    private lateinit var batch: SpriteBatch
    private lateinit var font: BitmapFont
    private lateinit var gsm : GameStateManager
    private lateinit var game : Game

    // Static variables / classes
    companion object {
        var WIDTH : Float = 480f
        var HEIGHT : Float = 800f
        var TITLE : String = "Idle Corporation Clicker"
    }

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        gsm = GameStateManager()
        game = this;
        game.setScreen(StartMenu(game,gsm))
//        MusicManager.play()
    }

    override fun render() {
        // Wipes the screen and redraw
        Gdx.input.setCatchKey(Input.Keys.BACK, true)
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK)){
            gsm.setNewScreenFromStack(gsm, game)
        }
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        super.render()
        //gsm.update(Gdx.graphics.deltaTime)
        //gsm.render(Gdx.graphics.deltaTime)
    }

    override fun dispose() {
        game.screen.dispose()
        MusicPlayer.dispose()
    }
}
