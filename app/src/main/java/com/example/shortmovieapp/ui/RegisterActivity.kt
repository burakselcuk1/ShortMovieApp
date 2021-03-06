package com.example.shortmovieapp.ui

import android.R.attr.password
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shortmovieapp.databinding.ActivityRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
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
            checkEmailExistsOrNot()
        }
    }

    private fun checkEmailExistsOrNot() {
        createNewUser()   }


    private fun createNewUser() {
        val registerEmail = binding.registerEmail.text.toString().trim()
        val registerPassword = binding.registerPassword.text.toString().trim()


        if(registerEmail.isEmpty()){
            Toast.makeText(this,"E-posta adresiniz boş olamaz!", Toast.LENGTH_SHORT).show()
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(registerEmail).matches()){
            Toast.makeText(this,"Geçersiz E-posta Adresi", Toast.LENGTH_SHORT).show()

        }
        else if(registerPassword.isEmpty()){
            Toast.makeText(this,"Şifreniz adresiniz boş olamaz!", Toast.LENGTH_SHORT).show()
        }
        else{
            firebaseAuth.createUserWithEmailAndPassword(registerEmail,registerPassword).addOnCompleteListener() {
                if (it.isSuccessful){
                    Toast.makeText(this,"Hesap Oluşturuldu!", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                }
            }.addOnFailureListener {
                Toast.makeText(this,"Böyle bir kullanıcı var!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}