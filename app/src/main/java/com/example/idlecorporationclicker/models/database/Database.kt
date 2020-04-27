package com.example.idlecorporationclicker.models.database

import android.util.Log
import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.models.player.PlayerOpponent
import com.example.idlecorporationclicker.views.PlayerList.PlayerList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

object Database: IDatabase {
    private val db = Firebase.firestore
    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    val SYNC_DELAY_SECONDS = 30 * 1000

    private var uid: String = ""
    private var localPlayer: Player? = null


    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            uid =  user.uid
        }
    }

    fun createOponentCollection(playerList: PlayerList) : MutableCollection<PlayerOpponent> {
        var dummyPlayer =
            PlayerOpponent(
                "-",
                "-",
                0,
                Date()
            )
        var players: MutableCollection<PlayerOpponent> = mutableListOf(dummyPlayer)
        players.clear()

        populateOpponentList(playerList)

        return players
    }

    override fun initiateLocalPlayer(player: Player) {
        DatabaseGetPlayer(player, true)
        localPlayer = player;
    }
    
    fun SyncMoneyWithDatabaseController(player: IPlayer) {
        if (Date().time > player.nextTimeToSync) {
            player.nextTimeToSync = Date().time + SYNC_DELAY_SECONDS
            databaseUpdateUsersMoney(player)
            updateTimeLastSyncedInDatabase(player)
        }
    }

    override fun buildingUpdateIncome() {
        databaseUpdateUsersSomething(localPlayer!!,"income", localPlayer!!.passiveIncomeBuilding.level)
        updateTimeLastSyncedInDatabase(localPlayer!!)
        databaseUpdateUsersMoney(localPlayer!!)
    }

    override fun buildingUpdateAttack() {
        databaseUpdateUsersSomething(localPlayer!!,"attack", localPlayer!!.attackBuilding.level)
        updateTimeLastSyncedInDatabase(localPlayer!!)
        databaseUpdateUsersMoney(localPlayer!!)
    }

    override fun buildingUpdateDefense() {
        databaseUpdateUsersSomething(localPlayer!!,"defense", localPlayer!!.defenseBuilding.level)
        updateTimeLastSyncedInDatabase(localPlayer!!)
        databaseUpdateUsersMoney(localPlayer!!)
    }


    override fun updateTimeLastSyncedInDatabase(player: IPlayer) {
        val date = Date()
        databaseUpdateUsersSomething(player, "timeLastSynced", date)

    }

    fun forceMoneySync(player: IPlayer = localPlayer!!) {
        databaseUpdateUsersMoney(player)
    }

    fun onResumeSyncMoneySinseLastSynced() {
        val lastSynced: Date? = getLastSynced()
        if (lastSynced != null) {
            localPlayer!!.addMoneySinceLastSynchedExternally(lastSynced)
        }
    }



    // -------------------------------------------     Database interactions     ------------------------------------------- \\



    fun stealFromPlayer(player: IPlayer, amountOfMoneyToSteal: Long) {
        db.collection("users")
            .document(player.uid)
            .get()
            .addOnSuccessListener { userDocument ->
                if(userDocument.data == null) {
                    databaseCreateUser(player)
                } else {
                    val moneyStolen : Long? = userDocument.getLong("moneyStolen")
                    if (moneyStolen != null) {
                        databaseUpdateUsersSomething(player, "moneyStolen", moneyStolen + amountOfMoneyToSteal)
                    } else {
                        databaseUpdateUsersSomething(player, "moneyStolen", amountOfMoneyToSteal)
                    }
                }
            }
    }

    private fun populateOpponentList(playerList: PlayerList) {
        db.collection("users")
            .get()
            .addOnSuccessListener { userCollection ->
                for (userDocument in userCollection) {
                    if (localPlayer!!.uid.equals(userDocument.id)) continue

                    val money : Long? = userDocument.getLong("money")
                    val name : String? = userDocument.getString("name")
                    val attack : Double? = userDocument.getDouble("attack")
                    val defense : Double? = userDocument.getDouble("defense")
                    val income : Double? = userDocument.getDouble("income")
                    val timeLastSynced : Date? = userDocument.getDate("timeLastSynced")
                    val moneyStolen : Long? = userDocument.getLong("moneyStolen")

                    if (money != null) {
                        if (name != null) {
                            if (attack != null) {
                                if (defense != null) {
                                    if (income != null) {
                                        if (timeLastSynced != null) {
                                            var opponent =
                                                PlayerOpponent(
                                                    userDocument.id,
                                                    name,
                                                    money,
                                                    timeLastSynced
                                                )
                                            opponent.uid = userDocument.id
                                            opponent.attackBuilding.setBuildingsStartLevel(attack)
                                            opponent.defenseBuilding.setBuildingsStartLevel(defense)
                                            opponent.passiveIncomeBuilding.setBuildingsStartLevel(income)
                                            opponent.addMoneySinceLastSynchedExternally(timeLastSynced)
                                            if (moneyStolen != null) {
                                                opponent.money -= moneyStolen
                                            }
                                            playerList.players.add(opponent)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    private fun DatabaseGetPlayer(player: Player, isLocal: Boolean) {
        db.collection("users")
            .document(player.uid)
            .get()
            .addOnSuccessListener { userDocument ->
                if(userDocument.data == null) {
                    databaseCreateUser(player)
                } else {
                    val name : String? = userDocument.getString("name")
                    val money : Long? = userDocument.getLong("money")
                    val attack : Double? = userDocument.getDouble("attack")
                    val defense : Double? = userDocument.getDouble("defense")
                    val income : Double? = userDocument.getDouble("income")
                    val lastSynced : Date? = userDocument.getDate("timeLastSynced")
                    val moneyStolen : Long? = userDocument.getLong("moneyStolen")

                    if (name != null) {
                        player.name = name
                    }
                    if (money != null) {
                        player.money = money
                    }
                    if (attack != null) {
                        player.attackBuilding.setBuildingsStartLevel(attack)
                    }
                    if (defense != null) {
                        player.defenseBuilding.setBuildingsStartLevel(defense)
                    }
                    if (income != null) {
                        player.passiveIncomeBuilding.setBuildingsStartLevel(income)
                    }
                    if (lastSynced != null) {
                        player.addMoneySinceLastSynchedExternally(lastSynced)
                        if (moneyStolen != null) {
                            player.money -= moneyStolen
                            if (isLocal)
                                databaseResetMoneyStolen(player)
                                databaseUpdateUsersMoney(player)
                        }
                    }

                }
            }
    }

    private fun getLastSynced() : Date? {
        var _lastSynced : Date? = null
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { userDocument ->
                if(userDocument.data == null) {
                    databaseCreateUser(localPlayer!!)
                } else {
                    val lastSynced : Date? = userDocument.getDate("timeLastSynced")
                    if (lastSynced != null) {
                        _lastSynced = lastSynced
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
        return _lastSynced
    }

    private fun databaseCreateUser(player: IPlayer) {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to player.name,
            "email" to player.email,
            "money" to 0,
            "income" to 1,
            "attack" to 1,
            "defense" to 1,
            "moneyStolen" to 0,
            "timeLastSynced" to Date()

        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(player.uid)
            .set(user)
    }

    fun databaseResetMoneyStolen(player: IPlayer) {
        db.collection("users")
            .document(player.uid)
            .update("moneyStolen", 0)
    }

    private fun databaseUpdateUsersMoney(player: IPlayer) {
        db.collection("users")
            .document(player.uid)
            .update("money", player.money)
            .addOnSuccessListener {
                updateTimeLastSyncedInDatabase(player)
            }
    }

    private fun databaseUpdateUsersSomething(player: IPlayer, something: String, value: Any) {
        db.collection("users")
            .document(player.uid)
            .update(something, value)
    }
}















