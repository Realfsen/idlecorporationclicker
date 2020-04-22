package com.example.idlecorporationclicker.states.playerlist

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.example.idlecorporationclicker.model.IAttack
import com.example.idlecorporationclicker.model.Player
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.State

class PlayerList(var attack: IAttack,
                 override var game: Game, override var gsm: GameStateManager
) : State(gsm, game) {


    private var bottom: Table
    private var chosenAttackStr: Label
    private var attackStr: Label
    private var sabotageStr: Label
    private var findPlayerStr: Label
    private var background : Texture
    private var playerTable : Table
    //private var chosenAttack: AttackScreen.attackType
    private var batch : SpriteBatch
    private var stage: Stage


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-attackmdpi.png"))
        playerTable = Table()

        //chosenAttack = attackType
        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3.5f)

        sabotageStr = Label("Sabotage", uiSkin)
        attackStr = Label("Steal", uiSkin)
        findPlayerStr = Label("Find player", uiSkin)
        chosenAttackStr = Label("Chosen attack: "+attack.type, uiSkin)
        stage = Stage()
        batch = SpriteBatch()


        var nameLabel = Label("Name", uiSkin)
        var defenseLabel = Label("Defense", uiSkin)
        var moneyLabel = Label("Money", uiSkin)
        var successLabel = Label("Success", uiSkin)


        playerTable.add(nameLabel);
        playerTable.add(defenseLabel);
        playerTable.add(moneyLabel);
        playerTable.add(successLabel);
        var player1 = Player()
        player1.money = 300
        player1.name = "Andreas"
        player1.defenseBuildings[0].level = 50.0
        player1.defenseBuildings[0].value = player1.defenseBuildings[0].calculateValue()
        var player2 = Player()
        player2.money = 5030
        player2.name = "Simon"
        player2.defenseBuildings[0].level = 10.0
        player2.defenseBuildings[0].value = player1.defenseBuildings[0].calculateValue()
        var player3 = Player()
        player3.money = 30
        player3.name = "Dag"
        player3.defenseBuildings[0].level = 5.0
        player3.defenseBuildings[0].value = player1.defenseBuildings[0].calculateValue()
        createNewPlayerRow(gsm.player, player1)
        createNewPlayerRow(gsm.player,player2)
        createNewPlayerRow(gsm.player,player3)
        playerTable.top().padTop(100f)
        playerTable.setFillParent(true)

        bottom = Table()
        bottom.add(chosenAttackStr)
        bottom.bottom().padBottom(30f)
        bottom.setFillParent(true)
        stage.addActor(playerTable)
        stage.addActor(bottom)
    }


    fun createNewPlayerRow(attacker: Player, defender: Player) {
        var uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)
        var btn = TextButton("Attack!", uiSkin)
        var name = Label(defender.name, uiSkin)
        var defense = Label(defender.defense().toInt().toString(), uiSkin)
        var successChance = Label("99%", uiSkin)
        var money = Label(defender.money.toInt().toString(), uiSkin)

        playerTable.row().pad(10f)
        playerTable.add(name)
        playerTable.add(defense)
        playerTable.add(money)
        playerTable.add(successChance)
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