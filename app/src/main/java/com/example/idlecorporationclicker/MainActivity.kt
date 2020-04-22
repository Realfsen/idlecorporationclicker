package com.example.idlecorporationclicker

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.idlecorporationclicker.firebase.FirebaseHandler
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
	val user = FirebaseAuth.getInstance().currentUser
	val RC_SIGN_IN = 131
	val providers = arrayListOf(
		AuthUI.IdpConfig.EmailBuilder().build())
//		AuthUI.IdpConfig.PhoneBuilder().build(),
//		AuthUI.IdpConfig.GoogleBuilder().build(),
//		AuthUI.IdpConfig.FacebookBuilder().build(),
//		AuthUI.IdpConfig.TwitterBuilder().build())



	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		if (user != null) {
			// The user is already signed in
			FirebaseHandler.start()
			setupHomeScreen()
			// ---------------- Signout code START:
			AuthUI.getInstance()
//				.signOut(this)
//				.addOnCompleteListener {
//					// ...
//				}
			// ---------------- Signout code END:
		} else {
			// No user is signed in
			showSignInOptions();
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
			Log.d("Login", response.toString());
			val test = response?.toString();
//			FirebaseHandler.setSignedInUser(response);
			FirebaseHandler.start()

			if (resultCode == Activity.RESULT_OK) {
				// Successfully signed in
				setupHomeScreen()

			} else {
				// Sign in failed. If response is null the user canceled the
				// sign-in flow using the back button. Otherwise check
				// response.getError().getErrorCode() and handle the error.

				val toast = Toast.makeText(applicationContext, "Failed to sign in.", Toast.LENGTH_LONG)
                toast.show()

				val intent = intent
				finish()
				startActivity(intent)
			}
		}
	}

	private fun setupHomeScreen() {
		setContentView(R.layout.activity_main)
		val navView: BottomNavigationView = findViewById(R.id.nav_view)
		val navController = findNavController(R.id.nav_host_fragment)

		//		 Passing each menu ID as a set of Ids because each
		//		 menu should be considered as top level destinations.
		val appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
			)
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)
	}


}
