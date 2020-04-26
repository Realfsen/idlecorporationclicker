package com.example.idlecorporationclicker.views

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

class Tutorial(var style : Label.LabelStyle, var screenWidth : Float, var screenHeight : Float) {

    private val circle : Image
    private val nextButton : TextButton
    private val text : Label
    private val uiSkin : Skin
    private var tutorialNumber : Int

    init {
        circle = Image(Texture(Gdx.files.internal("ui/circle/1x/circlemdpi.png")))
        uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        nextButton = TextButton("Next", uiSkin)
        text = Label("Click here to generate money", style)
        nextButton.setHeight(100f)
        nextButton.setWidth(200f)
        text.setPosition(screenWidth/2-text.width/2, screenHeight/3)
        circle.setPosition(screenWidth/2-circle.width/2, screenHeight/2 - circle.height/2)
        nextButton.setPosition(screenWidth/2-nextButton.width/2, screenHeight/1.5f-nextButton.height/2)

        uiSkin.getFont("default-font").getData().setScale(3f)
        tutorialNumber = 0

        nextButton.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                if(tutorialNumber ==0) {
                    stage2()
                    tutorialNumber++
                }
                else if(tutorialNumber == 1) {
                    stage3()
                    tutorialNumber++
                } else if(tutorialNumber ==2) {
                    stage4()
                }
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })
    }

    fun startTutorial(stage : Stage) {
        stage.addActor(circle)
        stage.addActor(text)
        stage.addActor(nextButton)
    }

    fun stage2() {
        text.setText("Buy buildings to increase\n your attack, defense \nand passive income")
        var moveToAction = MoveToAction()
        moveToAction.setPosition(30f, 60f)
        moveToAction.setDuration(1f)
        circle.addAction(moveToAction)
    }

    fun stage3() {
        text.setText("Steal money or sabotage\nfor other players")
        nextButton.setText("Finish")
        var moveToAction = MoveToAction()
        moveToAction.setPosition(screenWidth-circle.width - 130, 20f)
        moveToAction.setDuration(1f)
        circle.addAction(moveToAction)
    }
    fun stage4() {
        circle.remove()
        text.remove()
        nextButton.remove()
    }
}