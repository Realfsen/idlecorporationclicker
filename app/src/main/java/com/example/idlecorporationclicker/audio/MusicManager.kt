package com.example.idlecorporationclicker.audio
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

object MusicManager {
    private var track = Gdx.audio.newMusic(Gdx.files.internal("music/HappyJingle.mp3"))
    private var isPlaying = true
    private var musicButtonTable : Table

    init {
        musicButtonTable = Table()
        var musicBtn = Image(Texture(Gdx.files.internal("buttons/toggleMusicButtonOn.png")))
        musicBtn.addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                MusicManager.togglePlayState()
                musicBtn = Image(Texture("buttons/toggleMusicButtonOn.png"))
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
        isPlaying = true
    }

    fun pause() {
        track.pause()
        isPlaying = false
    }

    fun stop() {
        track.stop()
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