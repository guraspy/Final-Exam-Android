package com.example.finalexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class ForgotPasswordPage : AppCompatActivity() {

    private lateinit var forgotPasswordEditText : EditText
    private lateinit var forgotPasswordButton : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_page)

        forgotPasswordButton = findViewById(R.id.forgotButton)
        forgotPasswordEditText = findViewById(R.id.forgotEmailEditText)


        forgotPasswordButton.setOnClickListener {
            val email = forgotPasswordEditText.text.toString()
            if(email.isEmpty() || !email.contains("@")) return@setOnClickListener

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{
                if(it.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}