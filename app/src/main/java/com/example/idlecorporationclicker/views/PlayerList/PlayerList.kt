package com.example.idlecorporationclicker.views.PlayerList

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
import com.example.idlecorporationclicker.models.audio.MusicPlayer
import com.example.idlecorporationclicker.controllers.commands.attack.AttackPlayerCommand
import com.example.idlecorporationclicker.models.database.Database
import com.example.idlecorporationclicker.models.attack.IAttack
import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.models.player.PlayerOpponent
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.states.SCREEN
import com.example.idlecorporationclicker.views.ScreenTemplate

class PlayerList(var attack: IAttack,
                 override var game: Game, override var gsm: GameStateManager
) : ScreenTemplate(gsm, game) {
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

    private val nameLabel = Label("Name", fontStyle)
    private val defenseLabel = Label("Defense", fontStyle)
    private val moneyLabel = Label("Money", fontStyle)
    private val successLabel = Label("Success", fontStyle)


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-attackmdpi.png"))
        playerTable = Table()

        startTime = TimeUtils.nanoTime()

        //chosenAttack = attackType
        uiSkin.getFont("default-font").getData().setScale(3.5f)

        sabotageStr = Label("Sabotage", fontStyle)
        attackStr = Label("Steal", fontStyle)
        findPlayerStr = Label("Find player", fontStyle)
        chosenAttackStr = Label("Chosen attack: "+attack.type, fontStyle)
        stage = Stage()
        batch = SpriteBatch()

        players = Database.createOponentCollection(this)
        attackLabel = Label(createAttackLabelText(), fontStyle);

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
        stage.addActor(MusicPlayer.getMusicButtonTable())
        generateTopBar(stage, SCREEN.PlayerList, batch)
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
        val name = Label(defender.name, fontStyle)
        val defense = Label(defender.defense().toInt().toString(), fontStyle)
        val successChance = Label(attack.calculateSuccess(attacker, defender).toString()+"%", fontStyle)
        val money = Label(defender.money.toInt().toString(), fontStyle)
        val attackCommand =
            AttackPlayerCommand(
                attacker,
                defender,
                attack,
                this
            )

        playerTable.row().pad(10f)
        playerTable.add(name)
        playerTable.add(defense)
        playerTable.add(money)
        playerTable.add(successChance)
        playerTable.add(btn)


        btn.addListener(object : ClickListener() {
            override fun touchUp(e : InputEvent, x : Float, y : Float, Point : Int, button : Int) {
                println(attack.calculateSuccess(attacker, defender))
                gsm.commandManager.Invoke(attackCommand)
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


    override fun update() {
        playerTable.clear()
        generateTable()
    }


    override fun render(dt : Float) {

        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            startTime = TimeUtils.nanoTime();
            attackLabel.setText(createAttackLabelText())
            update()
        }
            batch.begin()
            batch.draw(
                background,
                0f,
                0f,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat()
            )
            updateTopBar(batch)
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