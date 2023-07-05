package com.example.finalexam

data class User(
    val email : String ?= null,
    val uid : String ?= null,
    val guess_count : Int? = 0,
    val game_won : Int? = 0,
    val username : String ?= null)
