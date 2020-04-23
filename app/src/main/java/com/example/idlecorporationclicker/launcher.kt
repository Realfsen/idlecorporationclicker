package com.example.idlecorporationclicker
import android.os.Bundle
import com.badlogic.gdx.backends.android.AndroidApplication
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration
import com.example.idlecorporationclicker.firebase.FirebaseController

class Launcher : AndroidApplication() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val config = AndroidApplicationConfiguration()
        initialize(IdleCorporationClicker(), config)
    }

    override fun onPause() {
        super.onPause()
        FirebaseController.goOfline()
    }

    override fun onResume() {
        super.onResume()
        FirebaseController.goOnline()
    }
}