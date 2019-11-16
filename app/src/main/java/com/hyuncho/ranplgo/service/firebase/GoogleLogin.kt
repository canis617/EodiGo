package com.hyuncho.ranplgo.service.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class GoogleLogin {
    private var mAuth = FirebaseAuth.getInstance();
    class GoogleLogin(){

    }

    public fun getUserId() : FirebaseUser? {
        return mAuth.currentUser;
    }
}