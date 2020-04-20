package com.example.idlecorporationclicker.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class HomeViewModel : ViewModel() {

	private val _text = MutableLiveData<String>().apply {
//		value = "This is home Fragment"
		value = "UserID: " + FirebaseAuth.getInstance().uid.toString()
	}
	val text: LiveData<String> = _text
}
