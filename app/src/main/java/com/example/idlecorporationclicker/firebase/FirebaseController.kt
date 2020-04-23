package com.example.idlecorporationclicker.firebase

import android.util.Log
import com.example.idlecorporationclicker.model.Player
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseController {
    private val realtimeDB = Firebase.database
    private val playersOnline = realtimeDB.getReference("playersOnline")

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var name: String = ""
    private var email: String = ""
    private var uid: String = ""
    private var money: Int = 0
    private var incomePerSecond: Int = 0
    private val db = Firebase.firestore

    private var isOnline: Boolean = false

    private var localPlayer: Player? = null


    init {
        val user = auth.currentUser
        if (user != null) {
            name = user.displayName.toString()
            email = user.email.toString()
            uid =  user.uid
        }
        Log.d("User: ", name + email + uid)
        firestoreGetUser()
        goOnline()
    }

    fun setSignedInUser(email: String, name: String, inNewUser: Boolean) {
        Log.d("Firebase", "setUser")
//        Log.d("Firebase", IdpResponse)
    }

    fun start() {

    }

    fun initiateLocalPlayer(player: Player) {
        localPlayer = player;
        localPlayer!!.name = this.name
        localPlayer!!.money = this.money
    }





    // -------------------------------------------     Firebase Firestire     ------------------------------------------- \\

    fun firestoreGetUser() {
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { userDocument ->
                Log.d("TAG", "${userDocument.id} => ${userDocument.data}")
                if(userDocument.data == null) {
                    firestoreCreateUser()
                } else {
                    val money : Long? = userDocument.getLong("money")
                    val incomePerSecond : Long? = userDocument.getLong("incomePerSecond")

                    if (money != null) {
                        this.money = money.toInt()
                    }
                    if (incomePerSecond != null) {
                        this.incomePerSecond = incomePerSecond.toInt()
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }
    }

    fun firestoreCreateUser() {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "money" to 0,
            "incomePerSecond" to 15
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

    fun firestoreUpdateUser() {
        db.collection("users")
            .document(uid)
            .update("money", localPlayer?.money)
        TODO("Update ALL active resources")
    }


// ------------------------------------------- Firebase realtime database ------------------------------------------- \\

    fun goOnline () {
        val user = hashMapOf(
            "name" to name,
            "email" to email,
            "income" to 0,
            "defenceBuildingsLevel" to 0,
            "defenceBuildingValue" to 0
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
        TODO("Move firestoreUpdater() to a better place")
    }

// -------------------------------------------  Firebase  Authentication  ------------------------------------------- \\

}















