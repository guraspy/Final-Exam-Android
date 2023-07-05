package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {

    private lateinit var username : TextView
    private lateinit var email : TextView
    private lateinit var gamesWon : TextView
    private lateinit var guessCount : TextView
    private lateinit var profileBackButton : Button

    val database = FirebaseDatabase.getInstance().getReference("User")


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        gamesWon = findViewById(R.id.gamesWon)
        guessCount = findViewById(R.id.guessCount)
        profileBackButton = findViewById(R.id.profileBackButton)


        val usrname = intent.getStringExtra("username");

        profileBackButton.setOnClickListener(){

            val intent = Intent(this, Game::class.java)
            intent.putExtra("username",username.toString())
            startActivity(intent)
            finish()
        }

        database.child(usrname!!).get().addOnSuccessListener {

            username.text = usrname
            email.text = it.child("email").value.toString()
            gamesWon.text = it.child("game_won").value.toString()
            guessCount.text = it.child("guess_count").value.toString()
        }



    }
}