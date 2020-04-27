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
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.TimeUtils
import com.badlogic.gdx.utils.viewport.ScreenViewport
import com.example.idlecorporationclicker.controllers.attack.AttackPlayerCommand
import com.example.idlecorporationclicker.controllers.player.PlayerController
import com.example.idlecorporationclicker.models.database.Database
import com.example.idlecorporationclicker.models.attack.IAttack
import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.models.player.PlayerOpponent
import com.example.idlecorporationclicker.states.GameStateManager
import com.example.idlecorporationclicker.views.actors.MenuActor
import com.example.idlecorporationclicker.views.ScreenTemplate
import java.util.*

class PlayerList(var attack: IAttack,
                 override var game: Game, override var gsm: GameStateManager
) : ScreenTemplate(gsm, game) {

    private var playerController: PlayerController
    private val screenHeight = Gdx.graphics.height.toFloat()
    private val screenWidth = Gdx.graphics.width.toFloat()
    private var shieldSymbol: Image
    private var cashSymbol: Image
    private var topWrapper: Table
    private var tableContainer: Container<Table>
    private var attackLabel: Label
    private var startTime: Long
    private var startTimeListUpdate: Long
    private var bottom: Table
    private var chosenAttackStr: Label
    private var attackStr: Label
    private var sabotageStr: Label
    private var findPlayerStr: Label
    private var background : Texture
    private var playerTable : Table
    private var batch : SpriteBatch
    private var stage: Stage

    var players : MutableCollection<PlayerOpponent>
    private val uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))

    private val nameLabel = Label("Name", fontStyle)
    private val successLabel = Label("%", fontStyle)
    private var menuActor : MenuActor


    init {
        background = Texture(Gdx.files.internal("backgrounds/1x/background-attackmdpi.png"))
        playerTable = Table()

        startTime = TimeUtils.nanoTime()
        startTimeListUpdate = TimeUtils.nanoTime()
        tableContainer = Container<Table>()

        var screenHeight = Gdx.graphics.height.toFloat()

        //chosenAttack = attackType
        uiSkin.getFont("default-font").getData().setScale(3.5f)

        stage = Stage(ScreenViewport(cam))
        sabotageStr = Label("Sabotage", fontStyle)
        attackStr = Label("Steal", fontStyle)
        findPlayerStr = Label("Find player", fontStyle)
        chosenAttackStr = Label("Chosen attack: "+attack.type, fontStyle)
        stage = Stage()
        batch = SpriteBatch()
        cashSymbol = Image(TextureRegion(gui, 1985, 4810, 145, 145))
        shieldSymbol = Image(TextureRegion(gui, 1680, 4810, 110, 145))
        cashSymbol.scaleY = 2f
        shieldSymbol.scaleY = 2f


        playerController = PlayerController(gsm.player, this)

        players = createEmptyOponentCollection()
        Database.populateOpponentList(this)
        attackLabel = Label(createAttackLabelText(), smolFontStyle);

        topWrapper = Table()
        topWrapper.setPosition(0f, screenHeight-screenHeight/6-topBar.regionHeight-300f)
        topWrapper.add(attackLabel).space(50f)
        topWrapper.row().padTop(50f)
        topWrapper.setWidth(Gdx.graphics.width.toFloat())
        generateTable()
        topWrapper.add(playerTable)
        bottom = Table()
        bottom.add(chosenAttackStr)
        bottom.bottom().padBottom(30f)
        bottom.setFillParent(true)
        stage.addActor(topWrapper)
        stage.addActor(bottom)
        generateTopBar(stage)
        menuActor =
            MenuActor(gsm)
        stage.addActor(menuActor.getActor())

    }

    fun updatePlayers() {
        Database.populateOpponentList(this)
    }

    fun createAttackLabelText() : String{
        if(!gsm.player.canAttack()) {
            return "You can attack in: "+gsm.player.secondsTillAttack()+" seconds"
        }
        return "You can attack now!"
    }

    fun createEmptyOponentCollection() : MutableCollection<PlayerOpponent> {
        var dummyPlayer =
            PlayerOpponent(
                "-",
                "-",
                0,
                Date()
            )
        var players: MutableCollection<PlayerOpponent> = mutableListOf(dummyPlayer)
        players.clear()
        return players
    }

    fun createNewPlayerRow(attacker: Player, defender: IPlayer) {
        val uiSkin = Skin(Gdx.files.internal("ui/uiskin.json"))
        uiSkin.getFont("default-font").getData().setScale(3f)
        val btn = TextButton("Attack!", uiSkin)
        if(!attacker.canAttack()) {
           btn.setColor(Color.RED)
        }
        val name = Label(defender.name, smolFontStyle)
        val defense = Label(defender.defense().toInt().toString(), smolFontStyle)
        val successChance = Label(attack.calculateSuccess(attacker, defender).toString()+"%", smolFontStyle)
        var tempMoney = defender.money.toInt()
        var moneyString = tempMoney.toString()
        if(tempMoney > 1000) {
            moneyString= (defender.money.toInt()/1000).toString()+"k"
        }
        val money = Label(moneyString, smolFontStyle)
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
        playerTable.add(defense).fillX().width(width).height(defense.height)
        playerTable.add(money).fillX().width(width).height(money.height)
        playerTable.add(successChance).width(width)
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
        playerTable.top()
        playerTable.add(nameLabel).fillX();
        playerTable.add(shieldSymbol)
        cashSymbol.setAlign(Align.bottom)
        playerTable.add(cashSymbol)
        playerTable.add(successLabel).width(screenWidth/6).right()
        players.forEach() {
            createNewPlayerRow(gsm.player, it)
        }
    }


    override fun update() {
        playerTable.clear()
        generateTable()
        updateTopBar()
        attackLabel.setText(createAttackLabelText())
    }


    override fun render(dt : Float) {

        if (TimeUtils.timeSinceNanos(startTime) > 1000000000) {
            startTime = TimeUtils.nanoTime();
            playerController.addMoneySinceLastSynch()
        }
        if (TimeUtils.timeSinceNanos(startTimeListUpdate) > 5000000000) {
            updatePlayers()
            startTimeListUpdate = TimeUtils.nanoTime();
        }
            batch.begin()
            batch.draw(
                background,
                0f,
                0f,
                Gdx.graphics.width.toFloat(),
                Gdx.graphics.height.toFloat()
            )
        drawTopBar(batch)
        if (menuOpen) {
                menuActor.show()
            } else {
                menuActor.hide()
            }
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