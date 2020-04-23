package com.example.idlecorporationclicker.audio
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.audio.Music

object MusicManager {
    private var track = Gdx.audio.newMusic(Gdx.files.internal("music/HappyJingle.mp3"))
    private var isPlaying = true

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
}