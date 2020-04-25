package com.example.idlecorporationclicker.models.audio
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable

object MusicPlayer {
    private var track = Gdx.audio.newMusic(Gdx.files.internal("music/HappyJingle.mp3"))
    private var isPlaying = true
    private var musicButtonTable : Table
    private var musicBtn : Image

    init {
        musicButtonTable = Table()
        musicBtn = Image(Texture("buttons/toggleMusicButtonOn.png"))
        musicBtn.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                togglePlayState()
            }
        })
        musicButtonTable.add(musicBtn).pad(10f).height(120f).width(120f)
        musicButtonTable.setFillParent(true)
        musicButtonTable.top().right()
    }

    fun getMusic(): Music {
        return track
    }

    fun play() {
        track.play()
        musicBtn.setDrawable(SpriteDrawable(Sprite(Texture("buttons/toggleMusicButtonOn.png"))))
        isPlaying = true
    }

    fun pause() {
        track.pause()
        musicBtn.setDrawable(SpriteDrawable(Sprite(Texture("buttons/toggleMusicButtonOff.png"))))
        isPlaying = false
    }

    fun stop() {
        track.stop()
        musicBtn.setDrawable(SpriteDrawable(Sprite(Texture("buttons/toggleMusicButtonOff.png"))))
        isPlaying = false
    }

    fun togglePlayState() {
        if (isPlaying == true) {
            stop()
        }
        else {
            play()
        }
    }

    fun isPlaying() : Boolean {
        return isPlaying
    }

    fun dispose() {
        track.dispose()
    }

    fun getMusicButtonTable() : Table {
       return musicButtonTable;
    }
}