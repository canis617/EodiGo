package com.hyuncho.ranplgo.data.user



class UserScore constructor(username : String, score: String) : Comparable<UserScore>{
    private var username  = username
    private var score : String = score

    fun getUser () : String{
        return username
    }

    fun getScore () : String {
        return score
    }

    override fun compareTo(other: UserScore): Int {
        if(Integer.parseInt(this.score) > Integer.parseInt(other.score)){
            return -1
        }
        else if(Integer.parseInt(this.score) == Integer.parseInt(other.score)){
            return 0
        }
        else {
            return 1
        }
    }
}
