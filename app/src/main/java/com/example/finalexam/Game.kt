package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class Game : AppCompatActivity() {

    private lateinit var from : EditText
    private lateinit var to : EditText
    private lateinit var enterNumber : EditText
    private lateinit var enterButton : Button
    private lateinit var output : TextView
    private lateinit var generateButton : Button
    private lateinit var profileButton : Button

    private val Fauth = FirebaseAuth.getInstance()

    val database = FirebaseDatabase.getInstance().getReference("User")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        from = findViewById(R.id.from)
        to = findViewById(R.id.to)
        enterNumber = findViewById(R.id.enterNumber)
        enterButton = findViewById(R.id.enterButton)
        output = findViewById(R.id.output)
        generateButton = findViewById(R.id.generateButton)
        profileButton = findViewById(R.id.profileButton)

        profileButton.setOnClickListener(){

            val usrname = intent.getStringExtra("username").toString()
            val profileintent = Intent(this, ProfileActivity::class.java)

            profileintent.putExtra("username",usrname)
            startActivity(profileintent)
        }


        var number = 0
        generateButton.setOnClickListener{
            if (from.text.toString().isDigitsOnly() || to.text.toString().isDigitsOnly()) {

                number = (from.text.toString().toInt()..to.text.toString().toInt()).random()

                Toast.makeText(this, "Number Generated!" + " from " + from.text.toString() + " to " + to.text.toString() + "guess number", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "wrong range!", Toast.LENGTH_SHORT).show()
            }
        }

        enterButton.setOnClickListener{

            val id = Fauth.currentUser!!.uid

            val username = intent.getStringExtra("username");




            if (enterNumber.text.toString().isDigitsOnly()) {

                if (number > enterNumber.text.toString().toInt()){
                    output.text = "Higher"

                }
                else if (number < enterNumber.text.toString().toInt()){
                    output.text = "Lower"
                }
                else{
                    output.text = "You Won!"
                }

                database.child(username!!).get().addOnSuccessListener {

                    val email = it.child("email").value
                    val game_won = it.child("game_won").value
                    val guess_count = it.child("guess_count").value



                    if (output.text == "You Won!")
                    {
                        val newuser = User(email.toString(),id,guess_count.toString().toInt() +1, game_won.toString().toInt()+1,username)

                        database.child(username).setValue(newuser).addOnCompleteListener(){

                        }.addOnFailureListener{
                                ex ->
                            Toast.makeText(this, "${ex.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        val newuser = User(email.toString(),id,guess_count.toString().toInt()+1,game_won.toString().toInt(),username)

                        database.child(username).setValue(newuser).addOnCompleteListener(){

                        }.addOnFailureListener{
                                ex ->
                            Toast.makeText(this, "${ex.message}" , Toast.LENGTH_SHORT).show()
                        }
                    }


                }.addOnFailureListener{

                }
            }
            else{
                Toast.makeText(this, "wrong input!", Toast.LENGTH_SHORT).show()
            }


        }
    }
}