package com.example.idlecorporationclicker
import android.content.Intent
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.example.idlecorporationclicker.models.database.Database
import com.firebase.ui.auth.AuthUI


class Launcher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        initialize(IdleCorporationClicker(this), config)
    }

    override fun onPause() {
        super.onPause()
//        Database.goOfline()
    }

    override fun onResume() {
        super.onResume()
//        Database.goOnline()
        Database.onResumeSyncMoneySinseLastSynced()
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