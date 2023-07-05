package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var emailEditText : EditText
    private lateinit var PasswordEditText : EditText
    private lateinit var sign_in : Button
    private lateinit var forgotButton : TextView
    private lateinit var sign_up : TextView
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailEditText = findViewById(R.id.email)
        PasswordEditText = findViewById(R.id.password)
        sign_in = findViewById(R.id.sign_in)
        forgotButton = findViewById(R.id.loginForgotPassword)
        sign_up = findViewById(R.id.sign_up)

        listeners()
    }

    private fun listeners() {
        sign_in.setOnClickListener()  {
            val email = emailEditText.text.toString()
            val password = PasswordEditText.text.toString()


            val username = intent.getStringExtra("username");




            if  (!email.contains("@"))
                return@setOnClickListener

            if (email.isEmpty() || password.isEmpty())
                return@setOnClickListener

            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                if (it.isSuccessful){
                    val intent = Intent(this, Game::class.java)
                    intent.putExtra("username",username)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        forgotButton.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordPage::class.java))
            finish()
        }

        sign_up.setOnClickListener {
            startActivity(Intent(this, RegisterPage::class.java))
            finish()
        }


    }
}