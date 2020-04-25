package com.example.idlecorporationclicker.models.database

import android.util.Log
import com.example.idlecorporationclicker.models.player.Player
import com.example.idlecorporationclicker.models.player.PlayerOpponent
import com.example.idlecorporationclicker.views.PlayerList.PlayerList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ktx.database
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
    private var playerList: PlayerList? = null
    private var nextTimeToSync: Long = Date().time + SYNC_DELAY_SECONDS
    private var timeLastSyncedFromDatabase: Date? = null

    init {
        val user: FirebaseUser? = auth.currentUser
        if (user != null) {
            name = user.displayName.toString()
            email = user.email.toString()
            uid =  user.uid
        }
        Log.d("User: ", name + email + uid)
        firestoreGetUser()
        goOnline()
    }

    fun start() {

    }

    fun createOponentCollection(playerList: PlayerList) : MutableCollection<PlayerOpponent> {
        this.playerList = playerList

        var dummyPlayer =
            PlayerOpponent(
                "-",
                "-",
                0,
                Date()
            )
        var players: MutableCollection<PlayerOpponent> = mutableListOf(dummyPlayer)
        players.clear()

        populateOpponentList()

        return players
    }

    override fun initiateLocalPlayer(player: Player) {
        firestoreGetUser()
        localPlayer = player;
    }
    
    fun SyncMoneyWithFirestoreController() {
//        money = localPlayer?.money
        if (Date().time > nextTimeToSync) {
            nextTimeToSync = Date().time + SYNC_DELAY_SECONDS
            firestoreUpdateUsersMoney()
            updateTimeLastSyncedInDatabase()
        }
    }

    fun playerUpdateMoney() {
//        firestoreUpdateUser()
    }

    override fun buildingUpdateIncome() {
        firestoreUpdateUsersSomething("income", localPlayer!!.passiveIncomeBuilding.level)
        updateTimeLastSyncedInDatabase()
    }

    override fun buildingUpdateAttack() {
        firestoreUpdateUsersSomething("attack", localPlayer!!.attackBuilding.level)
        updateTimeLastSyncedInDatabase()
    }

    override fun buildingUpdateDefense() {
        firestoreUpdateUsersSomething("defense", localPlayer!!.defenseBuilding.level)
        updateTimeLastSyncedInDatabase()
    }

    override fun updateTimeLastSyncedInDatabase() {
        firestoreUpdateUsersSomething("timeLastSynced", Date())
    }


    // -------------------------------------------     Firebase Firestire     ------------------------------------------- \\

    private fun populateOpponentList() {
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
                    if (money != null) {
                        if (name != null) {
                            if (attack != null) {
                                if (defense != null) {
                                    if (income != null) {
                                        if (timeLastSynced != null) {
                                            var opponent : PlayerOpponent =
                                                PlayerOpponent(
                                                    userDocument.id,
                                                    name,
                                                    money,
                                                    timeLastSynced
                                                )
                                            opponent.attackBuilding.setBuildingsStartLevel(attack)
                                            opponent.defenseBuilding.setBuildingsStartLevel(defense)
                                            opponent.passiveIncomeBuilding.setBuildingsStartLevel(income)
                                            playerList!!.players.add(opponent)
                                        }
                                    }
                                }
                            }
                        }
                    }


                }
            }
    }

    private fun firestoreGetUser() {
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { userDocument ->
//                Log.d("TAG", "${userDocument.id} => ${userDocument.data}")
                if(userDocument.data == null) {
                    firestoreCreateUser()
                } else {
                    val money : Long? = userDocument.getLong("money")
                    val attack : Double? = userDocument.getDouble("attack")
                    val defense : Double? = userDocument.getDouble("defense")
                    val income : Double? = userDocument.getDouble("income")

                    if (money != null) {
                        localPlayer!!.money = money
                    }
                    if (attack != null) {
                        localPlayer!!.attackBuilding.setBuildingsStartLevel(attack)
                    }
                    if (defense != null) {
                        localPlayer!!.defenseBuilding.setBuildingsStartLevel(defense)
                    }
                    if (income != null) {
                        localPlayer!!.passiveIncomeBuilding.setBuildingsStartLevel(income)
                    }
                    localPlayer!!.name = this.name
                    timeLastSyncedFromDatabase = Date()
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }

    private fun firestoreCreateUser() {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "money" to 0,
            "income" to 15,
            "attack" to 0,
            "defense" to 0,
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
    }

    private fun firestoreUpdateUsersSomething(something: String, value: Any) {
        db.collection("users")
            .document(uid)
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

// -------------------------------------------  Firebase  Authentication  ------------------------------------------- \\

}















