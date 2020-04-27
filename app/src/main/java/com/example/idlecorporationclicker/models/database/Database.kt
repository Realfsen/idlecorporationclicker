package com.example.idlecorporationclicker.models.database

import android.util.Log
import com.example.idlecorporationclicker.models.player.IPlayer
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.models.player.PlayerOpponent
import com.example.idlecorporationclicker.views.PlayerList.PlayerList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

object Database: IDatabase {
    private val realtimeDB = Firebase.database
    private val playersOnline = realtimeDB.getReference("playersOnline")
    private val SYNC_DELAY_SECONDS = 30 * 1000
    private val db = Firebase.firestore

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var name: String = ""
    private var email: String = ""
    private var uid: String = ""
    private var isOnline: Boolean = false
    private var localPlayer: Player? = null
//    private var playerList: PlayerList? = null
    private var nextTimeToSync: Long = Date().time + SYNC_DELAY_SECONDS
//    private var timeLastSyncedFromDatabase: Date? = null


    init {
        val settings = FirebaseFirestoreSettings.Builder()
            .setPersistenceEnabled(true)
            .build()
        db.firestoreSettings = settings

        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            name = user.displayName.toString()
            email = user.email.toString()
            uid =  user.uid
        }
        Log.d("User: ", name + email + uid)
        goOnline()
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
        player.uid = uid
        DatabaseGetPlayer(player, true)
        localPlayer = player;
    }
    
    fun SyncMoneyWithFirestoreController() {
        if (Date().time > nextTimeToSync) {
            nextTimeToSync = Date().time + SYNC_DELAY_SECONDS
            firestoreUpdateUsersMoney()
            updateTimeLastSyncedInDatabase()
        }
    }

    override fun buildingUpdateIncome() {
        firestoreUpdateUsersSomething("income", localPlayer!!.passiveIncomeBuilding.level)
        updateTimeLastSyncedInDatabase()
        firestoreUpdateUsersMoney()
    }

    override fun buildingUpdateAttack() {
        firestoreUpdateUsersSomething("attack", localPlayer!!.attackBuilding.level)
        updateTimeLastSyncedInDatabase()
        firestoreUpdateUsersMoney()
    }

    override fun buildingUpdateDefense() {
        firestoreUpdateUsersSomething("defense", localPlayer!!.defenseBuilding.level)
        updateTimeLastSyncedInDatabase()
        firestoreUpdateUsersMoney()
    }


    override fun updateTimeLastSyncedInDatabase() {
        val date = Date()
        firestoreUpdateUsersSomething("timeLastSynced", date)
//        timeLastSyncedFromDatabase = date

    }

    fun forceMoneySync() {
        firestoreUpdateUsersMoney()
    }

//    private fun timeLastSyncedFromDatabase(date: Date) {
//
//    }

    fun onResumeSyncMoneySinseLastSynced() {
        val lastSynced: Date? = getLastSynced()
        if (lastSynced != null) {
            localPlayer!!.addMoneySinceLastSynchedExternally(lastSynced)
        }
    }

    fun getPlayerMoney(player: IPlayer) {
        player.lastSynched
    }

    fun stealFromPlayer(player: IPlayer, amountOfMoneyToSteal: Long) {
        Log.d("STEAL", "StealFromPlayer DATABASE")
        Log.d("STEAL", "${player} ID: ${player.uid}")
        db.collection("users")
            .document(player.uid)
            .get()
            .addOnSuccessListener { userDocument ->
                if(userDocument.data == null) {
                    firestoreCreateUser()
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


    // -------------------------------------------     Firebase Firestire     ------------------------------------------- \\


    private fun populateOpponentList(playerList: PlayerList) {
        db.collection("users")
            .get()
            .addOnSuccessListener { userCollection ->
                for (userDocument in userCollection) {
                    if (uid.equals(userDocument.id)) continue

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
                    firestoreCreateUser()
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
                                DatabaseUpdateUsersMoney(player)
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
                    firestoreCreateUser()
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

    private fun firestoreCreateUser() {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "money" to 0,
            "income" to 1,
            "attack" to 1,
            "defense" to 1,
            "moneyStolen" to 0,
            "timeLastSynced" to Date()

        )

        // Add a new document with a generated ID
        db.collection("users")
            .document(uid.toString())
            .set(user)
            .addOnSuccessListener { documentReference ->
//                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)
            }
    }

    fun databaseResetMoneyStolen(player: IPlayer) {
//        var stolenMoney: Long = 0
//        db.collection("users")
//            .document(player.uid)
//            .get()
//            .addOnSuccessListener { userDocument ->
//                if(userDocument.data == null) {
//                    firestoreCreateUser()
//                } else {
//                    val money : Long? = userDocument.getLong("money")
//                    val moneyStolen : Long? = userDocument.getLong("moneyStolen")
//
//                    if (moneyStolen != null) {
//
//                    }
//                }
//            }
        db.collection("users")
            .document(player.uid)
            .update("moneyStolen", 0)
    }

    private fun firestoreUpdateUser() {
        db.collection("users")
            .document(uid)
            .update("money", localPlayer?.money)
//        TODO: "Update ALL active resources"
    }

    private fun firestoreUpdateUsersMoney() {
        db.collection("users")
            .document(uid)
            .update("money", localPlayer?.money)
            .addOnSuccessListener {
                updateTimeLastSyncedInDatabase()
            }
    }

    private fun databaseUpdateUsersMoney(player: IPlayer) {
        db.collection("users")
            .document(player.uid)
            .update("money", localPlayer?.money)
            .addOnSuccessListener {
                updateTimeLastSyncedInDatabase()
            }
    }

    private fun DatabaseUpdateUsersMoney(player: IPlayer) {
        db.collection("users")
            .document(player.uid)
            .update("money", player.money)
            .addOnSuccessListener {
                updateTimeLastSyncedInDatabase()
            }
    }

    private fun firestoreUpdateUsersSomething(something: String, value: Any) {
        db.collection("users")
            .document(uid)
            .update(something, value)
    }

    private fun databaseUpdateUsersSomething(player: IPlayer, something: String, value: Any) {
        db.collection("users")
            .document(player.uid)
            .update(something, value)
    }


// ------------------------------------------- Firebase realtime database ------------------------------------------- \\

    fun goOnline () {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "income" to 0,
            "money" to 0,
            "defenseBuildingsLevel" to 0,
            "defenseBuildingValue" to 0
        )

        playersOnline.child(uid.toString()).setValue(user)
            .addOnSuccessListener{
                isOnline = true
                Log.d("Firebase", "User is online = TRUE")
            }
    }

    fun goOfline() {
        playersOnline.child(uid.toString()).removeValue()
            .addOnSuccessListener{
                isOnline = false
                Log.d("Firebase", "User is online = TRUE")
            }
        firestoreUpdateUser()
        SyncMoneyWithFirestoreController()
//        TODO: "Move firestoreUpdater() to a better place"
    }
}















