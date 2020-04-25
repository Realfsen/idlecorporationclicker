package com.example.idlecorporationclicker.models.cookie

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.actions.Actions.*


class CookieClicker {
    private val images : List<Image>
    private var currentImage : Int

    init {
        var image1 = Image(Texture(Gdx.files.internal("money/3x/money1xxhdpi.png")))
        var image2 = Image(Texture(Gdx.files.internal("money/3x/money2xxhdpi.png")))
        var image3 = Image(Texture(Gdx.files.internal("money/3x/money3xxhdpi.png")))
        var image4 = Image(Texture(Gdx.files.internal("money/3x/money4xxhdpi.png")))
        var image5 = Image(Texture(Gdx.files.internal("money/3x/money5xxhdpi.png")))
        images = listOf(image1, image2, image3, image4, image5)
        currentImage = 0
    }

    private fun getNewImage() : Image {
        if(currentImage < images.size-2) {
            currentImage++
        } else {
            currentImage = 0
        }
        return images[currentImage]
    }

    fun getNextActor(x : Float, y : Float, screenHeight: Float) : Actor {
        var actor = getNewImage()
        actor.setPosition(x, y)
        actor.addAction(sequence(fadeIn(0.1f),moveTo(x, screenHeight-y, 0.5f), delay(0.5f), fadeOut(0.5f)))
        return actor
    }
}