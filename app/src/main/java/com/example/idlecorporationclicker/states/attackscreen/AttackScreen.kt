package com.example.idlecorporationclicker.states.attackscreen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.states.BuildingScreen.BuildingScreen
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.State

class AttackScreen(override var gsm: GameStateManager) : State(gsm) {

    enum class attackType {
        NONE,
        SABOTAGE,
        STEAL
    }

    private var chosenAttackStr: Label
    private var attackStr: Label
    private var sabotageStr: Label
    private var findPlayerStr: Label
    private var background : Texture
    private var player: Image
    private var attack: Image
    private var sabotage: Image
    private var attackTable : Table
    private var findPlayerTable : Table
    private var statsTable : Table
    private var chosenAttack: attackType


    private var stage : Stage

    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-attackmdpi.png"))
        player = Image(Texture(Gdx.files.internal("attacks/1x/playermdpi.png")))
        attack = Image(Texture(Gdx.files.internal("attacks/1x/stealmdpi.png")))
        sabotage = Image(Texture(Gdx.files.internal("attacks/1x/politicsmdpi.png")))
        attackTable = Table()
        findPlayerTable = Table()
        statsTable = Table()
        chosenAttack = attackType.NONE;
        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(5f)

        sabotageStr = Label("Sabotage", uiSkin)
        attackStr = Label("Steal", uiSkin)
        findPlayerStr = Label("Find player", uiSkin)
        chosenAttackStr = Label("Chosen attack: "+chosenAttack, uiSkin)

        attack.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                chosenAttack = attackType.STEAL;
                chosenAttackStr.setText("Chosen attack: "+chosenAttack)
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        sabotage.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                chosenAttack = attackType.SABOTAGE;
                chosenAttackStr.setText("Chosen attack: "+chosenAttack)
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })

        player.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                //TODO go to player list
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })



        buildTablesAndAddActors();
        stage = Stage(ScreenViewport(cam))
        stage.addActor(attackTable)
        stage.addActor(findPlayerTable)
        stage.addActor(statsTable)
        Gdx.input.setInputProcessor(stage)
    }

    fun buildTablesAndAddActors() {
        attackTable.add(sabotage).expandX()
        attackTable.add(attack).expandX()
        attackTable.bottom()
        attackTable.setFillParent(true)
        attackTable.row()

        attackTable.add(sabotageStr)
        attackTable.add(attackStr)

        findPlayerTable.add(player)
        findPlayerTable.row()
        findPlayerTable.add(findPlayerStr)
        findPlayerTable.setFillParent(true)

        statsTable.add(chosenAttackStr)
        statsTable.setFillParent(true)
        statsTable.top()
    }

    override fun handleInput() {
    }

    override fun update(dt: Float) {
    }

    override fun render(sb: SpriteBatch) {
        sb.begin()
        sb.draw(background, 0f, 0f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        sb.end()
        stage.act(Gdx.graphics.deltaTime)
        stage.draw()
    }
}