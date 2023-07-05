package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase


class RegisterPage : AppCompatActivity() {

    private lateinit var signUpEmailEditText : EditText
    private lateinit var signUpPassEditText : EditText
    private lateinit var signUpBtn : Button
    private lateinit var signInBtn : TextView
    private lateinit var signUpUsernameEditText : EditText

    private val Fauth = FirebaseAuth.getInstance()

    val database = FirebaseDatabase.getInstance().getReference("User")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        signUpEmailEditText = findViewById(R.id.signUpEmailEditText)
        signUpPassEditText = findViewById(R.id.signUpPasswordEditText)
        signUpBtn = findViewById(R.id.singUpButton)
        signInBtn = findViewById(R.id.signInButton)
        signUpUsernameEditText = findViewById(R.id.signUpUsernameEditText)



        listeners()
    }

    private fun listeners() {

        signUpBtn.setOnClickListener {

            val email = signUpEmailEditText.text.toString()
            val password = signUpPassEditText.text.toString()

            if (email.isEmpty() || password.isEmpty() || !email.contains("@") ||
                password.length < 12){
                return@setOnClickListener
            }

            Fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if (task.isSuccessful){
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("username",signUpUsernameEditText.text.toString())
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            //val id = database.push().key!!
            val id = Fauth.currentUser!!.uid

            val User = User(email, id,0,0)

            database.child(signUpUsernameEditText.text.toString()).setValue(User).addOnCompleteListener(){

            }.addOnFailureListener{
                    ex ->
                Toast.makeText(this, "${ex.message}", Toast.LENGTH_SHORT).show()
            }
        }

        signInBtn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}