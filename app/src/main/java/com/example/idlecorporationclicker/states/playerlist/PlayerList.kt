package com.example.idlecorporationclicker.states.playerlist

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.State
import com.example.idlecorporationclicker.states.attackscreen.AttackScreen

class PlayerList(var attackType: AttackScreen.attackType,
                 override var game: Game, override var gsm: GameStateManager
) : State(gsm, game) {


    private var bottom: Table
    private var chosenAttackStr: Label
    private var attackStr: Label
    private var sabotageStr: Label
    private var findPlayerStr: Label
    private var background : Texture
    private var playerTable : Table
    private var chosenAttack: AttackScreen.attackType
    private var batch : SpriteBatch
    private var stage: Stage


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-attackmdpi.png"))
        playerTable = Table()

        chosenAttack = attackType
        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3.5f)

        sabotageStr = Label("Sabotage", uiSkin)
        attackStr = Label("Steal", uiSkin)
        findPlayerStr = Label("Find player", uiSkin)
        chosenAttackStr = Label("Chosen attack: "+chosenAttack, uiSkin)
        stage = Stage()
        batch = SpriteBatch()


        var nameLabel = Label("Name", uiSkin)
        var defenseLabel = Label("Defense", uiSkin)
        var moneyLabel = Label("Money", uiSkin)
        playerTable.add(nameLabel);
        playerTable.add(defenseLabel);
        playerTable.add(moneyLabel);
        createNewPlayerRow()
        createNewPlayerRow()
        createNewPlayerRow()
        createNewPlayerRow()
        createNewPlayerRow()
        createNewPlayerRow()
        playerTable.top().padTop(100f)
        playerTable.setFillParent(true)

        bottom = Table()
        bottom.add(chosenAttackStr)
        bottom.bottom().padBottom(30f)
        bottom.setFillParent(true)
        stage.addActor(playerTable)
        stage.addActor(bottom)
    }


    fun createNewPlayerRow() {
        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)
        var btn = TextButton("Attack!", uiSkin)
        var name = Label("Player", uiSkin)
        var defense = Label("300", uiSkin)
        var money = Label("999", uiSkin)

        playerTable.row().pad(10f)
        playerTable.add(name)
        playerTable.add(defense)
        playerTable.add(money)
        playerTable.add(btn)


        btn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                //TODO attack player
                println("ATTACK!")
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })


    }
    override fun handleInput() {
    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(dt : Float) {
            update(dt)
            batch.begin()
            batch.draw(
                background,
                0f,
                0f,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat()
            )
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