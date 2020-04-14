package com.example.idlecorporationclicker
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.StartMenu


public class IdleCorporationClicker : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    //private lateinit var img: Texture
    private lateinit var font: BitmapFont
    private lateinit var gsm : GameStateManager

    // Static variables / classes
    companion object{
        var WIDTH : Int = 480
        var HEIGHT : Int = 800
        var TITLE : String = "Idle Corporation Clicker"
    }

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        gsm = GameStateManager()
        Gdx.gl.glClearColor(1f, 0f, 0f, 1f)
        gsm.push(StartMenu(gsm))
    }

    override fun render() {
        // Wipes the screen and redraw
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
//        batch.begin()
//        if (font.scaleX < 30) {
//            font.data.scale(1.1f)
//        }
//
//        font.draw(batch, "Hello", 50f, 400f)
//        batch.end()
        gsm.update(Gdx.graphics.deltaTime)
    }

    override fun dispose() {
        batch.dispose()
        //img.dispose()
    }
}
