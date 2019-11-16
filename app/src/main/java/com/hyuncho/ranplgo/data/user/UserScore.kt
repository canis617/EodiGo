package com.hyuncho.ranplgo.data.user

import com.google.firebase.auth.FirebaseUser

class UserScore (user : FirebaseUser, score: Int, mode: String){
    private var user : FirebaseUser? = user
    private var score : Int = score
    private var mode : String = mode
}
