package com.example.shortmovieapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.shortmovieapp.MainActivity
import com.example.shortmovieapp.R
import com.example.shortmovieapp.databinding.ActivityLoginBinding
import com.example.shortmovieapp.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //  Checks if already logged in
        checkUser()

        //Goes to register.xml for create new account
        binding.registerTextview.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            singIn()
        }
    }

    // singin into application
    private fun singIn() {

        val loginemail = binding.loginEmail.text.toString().trim()
        val loginPassword = binding.loginPassword.text.toString().trim()

        //check e-mail empty or not
        if(loginemail.isEmpty()){
            Toast.makeText(this,"E-posta adresiniz boş olamaz!", Toast.LENGTH_SHORT).show()
        }
        // compare e-mail adreess with real e-mail type
        else if(!Patterns.EMAIL_ADDRESS.matcher(loginemail).matches()){
            Toast.makeText(this,"Geçersiz E-posta adresi!", Toast.LENGTH_SHORT).show()

        }
        //check password is empty or not
        else if(loginPassword.isEmpty()){
            Toast.makeText(this,"Şifreniz boş olamaz!!", Toast.LENGTH_SHORT).show()
        }
        else{
            firebaseAuth.signInWithEmailAndPassword(loginemail,loginPassword).addOnSuccessListener {
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener{
                Toast.makeText(this,"Kullanıcı Bulunamadı!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkUser() {
        val firebase = firebaseAuth.currentUser
        if (firebase != null){
            Toast.makeText(this,"Hoşgeldiniz", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
    }
