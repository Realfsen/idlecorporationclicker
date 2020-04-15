package com.example.idlecorporationclicker
import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.startmenu.StartMenu


public class IdleCorporationClicker : ApplicationAdapter() {
    private lateinit var batch: SpriteBatch
    //private lateinit var img: Texture
    private lateinit var font: BitmapFont
    private lateinit var gsm : GameStateManager

    // Static variables / classes
    companion object{
        var WIDTH : Float = 480f
        var HEIGHT : Float = 800f
        var TITLE : String = "Idle Corporation Clicker"
    }

    override fun create() {
        batch = SpriteBatch()
        font = BitmapFont()
        gsm = GameStateManager()
        gsm.push(StartMenu(gsm))
    }

    override fun render() {
        // Wipes the screen and redraw
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        gsm.update(Gdx.graphics.deltaTime)
        gsm.render(batch)
    }

    override fun dispose() {
        batch.dispose()
        //img.dispose()
    }
}
