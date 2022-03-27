package com.example.shortmovieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.shortmovieapp.R
import com.example.shortmovieapp.databinding.ActivityLoginBinding
import com.example.shortmovieapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.registerButton.setOnClickListener {
            createNewUser()

        }
    }

    private fun createNewUser() {
        val registerEmail = binding.registerEmail.text.toString().trim()
        val registerPassword = binding.registerPassword.text.toString().trim()


        if(registerEmail.isEmpty()){
            Toast.makeText(this,"E-mail can not be empty!", Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(registerEmail).matches()){
            Toast.makeText(this,"Invalid E-mail adress!", Toast.LENGTH_SHORT).show()

        }
        else if(registerPassword.isEmpty()){
            Toast.makeText(this,"Password can not be empty!", Toast.LENGTH_SHORT).show()
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(registerEmail,registerPassword).addOnSuccessListener {
                Toast.makeText(this,"User Created!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this,"Error!", Toast.LENGTH_SHORT).show()

            }

        }
    }
}