package com.example.idlecorporationclicker.states.playerlist

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import com.example.idlecorporationclicker.audio.MusicManager
import com.example.idlecorporationclicker.commands.AttackPlayerCommand
import com.example.idlecorporationclicker.database.DatabaseController
import com.example.idlecorporationclicker.model.IAttack
import com.example.idlecorporationclicker.model.IPlayer
import com.example.idlecorporationclicker.model.Player
import com.example.idlecorporationclicker.model.PlayerOpponent
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.State

class PlayerList(var attack: IAttack,
                 override var game: Game, override var gsm: GameStateManager
) : State(gsm, game) {


    private var topWrapper: Table
    private var attackLabel: Label
    private var startTime: Long
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

    var players : MutableCollection<PlayerOpponent>
    private val uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))

    private val nameLabel = Label("Name", uiSkin)
    private val defenseLabel = Label("Defense", uiSkin)
    private val moneyLabel = Label("Money", uiSkin)
    private val successLabel = Label("Success", uiSkin)


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-attackmdpi.png"))
        playerTable = Table()

        startTime = TimeUtils.nanoTime()

        //chosenAttack = attackType
        uiSkin.getFont("default-font").getData().setScale(3.5f)

        sabotageStr = Label("Sabotage", uiSkin)
        attackStr = Label("Steal", uiSkin)
        findPlayerStr = Label("Find player", uiSkin)
        chosenAttackStr = Label("Chosen attack: "+attack.type, uiSkin)
        stage = Stage()
        batch = SpriteBatch()

        players = DatabaseController.createOponentCollection(this)
        attackLabel = Label(createAttackLabelText(), uiSkin);

        topWrapper = Table()
        topWrapper.add(attackLabel)
        topWrapper.row().padTop(30f)
        topWrapper.top()
        topWrapper.setFillParent(true)
        generateTable()
        topWrapper.add(playerTable)
        bottom = Table()
        bottom.add(chosenAttackStr)
        bottom.bottom().padBottom(30f)
        bottom.setFillParent(true)
        stage.addActor(playerTable)
        stage.addActor(topWrapper)
        stage.addActor(bottom)
        stage.addActor(MusicManager.getMusicButtonTable())
    }

    fun createAttackLabelText() : String{
        if(!gsm.player.canAttack()) {
            return "You can attack in: "+gsm.player.secondsTillAttack()+" seconds"
        }
        return "You can attack now!"
    }

    fun createNewPlayerRow(attacker: Player, defender: IPlayer) {
        val uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)
        val btn = TextButton("Attack!", uiSkin)
        if(!attacker.canAttack()) {
           btn.setColor(Color.RED)
        }
        val name = Label(defender.name, uiSkin)
        val defense = Label(defender.defense().toInt().toString(), uiSkin)
        val successChance = Label(attack.calculateSuccess(attacker, defender).toString()+"%", uiSkin)
        val money = Label(defender.money.toInt().toString(), uiSkin)
        val attackCommand = AttackPlayerCommand(attacker, defender, attack)

        playerTable.row().pad(10f)
        playerTable.add(name)
        playerTable.add(defense)
        playerTable.add(money)
        playerTable.add(successChance)
        playerTable.add(btn)


        btn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                println(attack.calculateSuccess(attacker, defender))
                if(attackCommand.CanExecute()) {
                   attackCommand.Execute()
                }
                playerTable.clear()
                generateTable()
            }
            override fun touchDown(e : InputEvent, x : Float, y : Float, Point : Int, button : Int): Boolean {
                return true
            }
        })
    }

    fun generateTable() {
        playerTable.add(nameLabel);
        playerTable.add(defenseLabel);
        playerTable.add(moneyLabel);
        playerTable.add(successLabel);
        players.forEach() {
            createNewPlayerRow(gsm.player, it)
        }

        playerTable.top().padTop(100f)
        playerTable.setFillParent(true)
    }

    override fun handleInput() {
    }

    override fun update(dt: Float) {
        handleInput()
    }

    override fun render(dt : Float) {
            update(dt)

        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            startTime = TimeUtils.nanoTime();
            attackLabel.setText(createAttackLabelText())
            playerTable.clear()
            generateTable()
        }
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