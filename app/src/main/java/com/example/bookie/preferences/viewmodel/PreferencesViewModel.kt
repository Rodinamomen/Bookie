package com.example.bookie.preferences.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PreferencesViewModel():ViewModel() {
    var db= Firebase.firestore
    var auth = FirebaseAuth.getInstance()
    private val _preferencesAdded = MutableLiveData<Boolean>()
    val preferencesAdded: LiveData<Boolean> = _preferencesAdded
    fun addPreferences(selectedItems:MutableSet<String>){
        val preferencesCollection = db.collection("users")
            .document(auth.currentUser?.uid.toString())
            .collection("Preferences")
        for(item in selectedItems){
            val data = mapOf("preference" to item)
            preferencesCollection.add(data).addOnSuccessListener {
                Log.d("preferences", "addPreferences: added ")
                _preferencesAdded.value= true
            }.addOnFailureListener {
                Log.d("preferences", "addPreferences: !!!!!!!! ")
                _preferencesAdded.value= false
            }
        }
    }
}