package com.example.idlecorporationclicker.views.PlayerList

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.*
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.FillViewport
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.badlogic.gdx.utils.viewport.StretchViewport
import com.example.idlecorporationclicker.models.audio.MusicPlayer
import com.example.idlecorporationclicker.controllers.commands.attack.AttackPlayerCommand
import com.example.idlecorporationclicker.models.database.Database
import com.example.idlecorporationclicker.models.attack.IAttack
import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.models.player.PlayerOpponent
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.views.ScreenTemplate

class PlayerList(var attack: IAttack,
                 override var game: Game, override var gsm: GameStateManager
) : ScreenTemplate(gsm, game) {
    private var shieldSymbol: Image
    private var cashSymbol: Image
    private var gui: Texture
    private var topWrapper: Table
    private var tableContainer: Container<Table>
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

    private val nameLabel = Label("Name", gsm.fontStyle)
    private val defenseLabel = Label("Defense", gsm.fontStyle)
    private val moneyLabel = Label("Money", gsm.fontStyle)
    private val successLabel = Label("%", gsm.fontStyle)


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-attackmdpi.png"))
        playerTable = Table()
        playerTable.setWidth(Gdx.graphics.width.toFloat())

        startTime = TimeUtils.nanoTime()
        tableContainer = Container<Table>()
        val sw: Float = Gdx.graphics.getWidth().toFloat()
        val sh: Float = Gdx.graphics.getHeight().toFloat()

        tableContainer.setSize(480f, 600f)
        tableContainer.top().left()
        tableContainer.setFillParent(true)
        //tableContainer.setPosition(0f, 0f)
        //tableContainer.fillX()

        //chosenAttack = attackType
        uiSkin.getFont("default-font").getData().setScale(3.5f)

        sabotageStr = Label("Sabotage", gsm.fontStyle)
        attackStr = Label("Steal", gsm.fontStyle)
        findPlayerStr = Label("Find player", gsm.fontStyle)
        chosenAttackStr = Label("Chosen attack: "+attack.type, gsm.fontStyle)
        stage = Stage(ScreenViewport(cam))
        batch = SpriteBatch()
        gui = Texture(Gdx.files.internal("freegui/png/Window.png"))
        cashSymbol = Image(TextureRegion(gui, 1985, 4810, 95, 145))
        shieldSymbol = Image(TextureRegion(gui, 1680, 4810, 110, 145))

        tableContainer.setDebug(true)
        players = Database.createOponentCollection(this)
        attackLabel = Label(createAttackLabelText(), gsm.fontStyle);

        topWrapper = Table()
        topWrapper.add(attackLabel)
        topWrapper.row().padTop(30f)
        //topWrapper.top()
        //topWrapper.setFillParent(true)
        topWrapper.setWidth(Gdx.graphics.width.toFloat())
        generateTable()
        topWrapper.add(playerTable)
        bottom = Table()
        bottom.add(chosenAttackStr)
        bottom.bottom().padBottom(30f)
        bottom.setFillParent(true)
        tableContainer.setActor(topWrapper)
        stage.addActor(tableContainer)
        stage.addActor(bottom)
        stage.addActor(MusicPlayer.getMusicButtonTable())
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
        val attackCommand =
            AttackPlayerCommand(
                attacker,
                defender,
                attack,
                this
            )

        var width = Gdx.graphics.width.toFloat()/6
        playerTable.row().pad(10f)
        playerTable.add(name).fillX().width(width)
        playerTable.add(defense).fillX().width(width)
        playerTable.add(money).grow().expandX().width(money.width)
        playerTable.add(successChance).fillX().width(width)
        playerTable.add(btn).fillX().width(width)


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
        playerTable.setWidth(Gdx.graphics.width.toFloat())
        playerTable.add(nameLabel).fillX();
        playerTable.add(shieldSymbol)
        playerTable.add(cashSymbol)
        playerTable.add(successLabel).fillX();
        players.forEach() {
            createNewPlayerRow(gsm.player, it)
        }

        //playerTable.top().padTop(100f)
        //playerTable.setWidth(Gdx.graphics.width.toFloat())
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