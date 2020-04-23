package com.example.idlecorporationclicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.idlecorporationclicker.firebase.FirebaseController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {
	val user = FirebaseAuth.getInstance().currentUser
	val RC_SIGN_IN = 131
	val providers = arrayListOf(
		AuthUI.IdpConfig.EmailBuilder().build())
//		AuthUI.IdpConfig.GoogleBuilder().build(),



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (user != null) {
			// The user is already signed in
			FirebaseController.start()
			launchGame()
//			signOut()
		} else {
			// No user is signed in
			showSignInOptions();
		}
	}

	private fun signOut() {
		AuthUI.getInstance()
			.signOut(this)
			.addOnCompleteListener {
				val intent = intent
				finish()
				startActivity(intent)
			}
	}

	private fun showSignInOptions() {
		startActivityForResult(
			AuthUI.getInstance()
				.createSignInIntentBuilder()
				.setAvailableProviders(providers)
				.setIsSmartLockEnabled(true)
				.setTheme(R.style.LoginTheme)
				.build(), RC_SIGN_IN
		)
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)

		if (requestCode == RC_SIGN_IN) {
			val response = IdpResponse.fromResultIntent(data)
			FirebaseController.start()

			if (resultCode == Activity.RESULT_OK) {
				// Successfully signed in
				launchGame()

			} else {
				// Sign in failed. If response is null the user canceled the
				val toast = Toast.makeText(applicationContext, "Failed to sign in.", Toast.LENGTH_LONG)
                toast.show()

				val intent = intent
				finish()
				startActivity(intent)
			}
		}
	}

	private fun launchGame() {
		launchlibGDX()
	}
	fun launchlibGDX() {
		val intent = Intent(this, Launcher::class.java)
		startActivity(intent)
	}



}
