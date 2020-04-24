package com.example.idlecorporationclicker
import android.content.Intent
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.example.idlecorporationclicker.database.DatabaseController
import com.firebase.ui.auth.AuthUI


class Launcher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        initialize(IdleCorporationClicker(), config)
    }

    override fun onPause() {
        super.onPause()
        DatabaseController.goOfline()
    }

    override fun onResume() {
        super.onResume()
        DatabaseController.goOnline()
    }

    fun signOut() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, MainActivity::class.java)
                finish()
                startActivity(intent)
            }
    }
}