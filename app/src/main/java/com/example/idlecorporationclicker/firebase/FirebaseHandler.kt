package com.example.idlecorporationclicker.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseHandler {

    var auth: FirebaseAuth? = null
    var name: String? = null
    var email: String? = null
    var uid: String? = null

    val db = Firebase.firestore


    init {
        auth = FirebaseAuth.getInstance()
        val user = auth!!.currentUser
        if (user != null) {
            name = user.displayName
            email = user.email
            uid =  user.uid
        }
        Log.d("User: ", name + email + uid)
        firestoreGetUser()
    }

    fun setSignedInUser(email: String, name: String, inNewUser: Boolean) {
        Log.d("Firebase", "setUser")
//        Log.d("Firebase", IdpResponse)
    }

    fun start() {

    }

    fun firestoreGetUser() {
        db.collection("users")
            .document(uid.toString())
            .get()
            .addOnSuccessListener { userDocument ->
                Log.d("TAG", "${userDocument.id} => ${userDocument.data}")
                if(userDocument.data == null) {
                    firestoreCreateUser()
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
            "income" to 0
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

}