/*
 *  https://firebase.google.com/docs/database/android/read-and-write?hl=ko
 */

package com.hyuncho.ranplgo.service.firebase

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class FireBaseDB{
    // Write a message to the database
    private lateinit var database : FirebaseDatabase
    private lateinit var myRef : DatabaseReference
    private var user : FirebaseUser?

    init {
        initDB()
    }
    constructor(user: FirebaseUser?){
        this.user = user
        user?.let {
            /*
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
            */

        }
    }

    private fun initDB () {
        database = FirebaseDatabase.getInstance()
        myRef = database.getReference("message")

        myRef.setValue("구글 파이어베이스 데이터 베이스 확인!")
    }

    public fun createGameData(){
        myRef = database.getReference("gamedata/ongoing/"+user?.uid)
        myRef.setValue("game data!")
    }

    public fun updateResult(){
        myRef = database.getReference("gamedata/result/"+user?.displayName)
        myRef.setValue("your score!")
    }

}