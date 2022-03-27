package com.example.shortmovieapp.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.shortmovieapp.R
import com.example.shortmovieapp.databinding.FragmentUpdateProfileBinding
import com.example.shortmovieapp.model.FirebaseUserProfile
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_update_profile.*
import java.text.SimpleDateFormat
import java.util.*


class UpdateProfileFragment : Fragment() {

    private var _binding : FragmentUpdateProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var auth : FirebaseAuth
    lateinit var ImageUrl : Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentUpdateProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // User can select a image for profile pic
        binding.uploadImage.setOnClickListener {
            selectImage()
        }

        // update profile progress
        binding.updateUserButton.setOnClickListener {
            uploadImageToFirebaseStorage()
        }
    }

    private fun uploadImageToFirebaseStorage() {
        val username = binding.updateUserUsername.text.toString()
        val phone = binding.updateUserPhone.text.toString()

        //Checking Phone is Empty or not
         if (phone.isEmpty()){
            Toast.makeText(context,"Lütfen Telefon Numaranızı Girin", Toast.LENGTH_SHORT).show()
        }
        //Checking Username is Empty or not
        else if (username.isEmpty()){
            Toast.makeText(context,"Lütfen Kullanıcı Adınızı Girin", Toast.LENGTH_SHORT).show()

        }
        //Checking Image is Empty or not
        else if (circleImage.getDrawable() == null){
            Toast.makeText(context,"Lütfen Profil Resminizi Seçin", Toast.LENGTH_SHORT).show()

        }
        else{
            val progressDialog = ProgressDialog(context)
            progressDialog.setMessage("Yükleniyor...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)
            val storage = FirebaseStorage.getInstance().getReference("images/$fileName")

            storage.putFile(ImageUrl)
                .addOnSuccessListener {
                    binding.uploadImage.setImageURI(null)
                    Toast.makeText(context,"BAŞARILI!", Toast.LENGTH_SHORT).show()
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    storage.downloadUrl.addOnSuccessListener {
                        Log.d("UpdateProfileFragment","File Location: $it")
                        saveUserToFirebaseDatabase(it.toString())
                        findNavController().navigate(R.id.action_updateProfileFragment_to_profileFragment2)
                    }
                }.addOnFailureListener{
                    if (progressDialog.isShowing) progressDialog.dismiss()
                    Toast.makeText(context,"FAIL", Toast.LENGTH_SHORT).show()
          }
       }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        database = FirebaseDatabase.getInstance().getReference("users")
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        val username = binding.updateUserUsername.text.toString()
        val phone = binding.updateUserPhone.text.toString()

        if (uid!= null){
            database.child(uid).setValue(FirebaseUserProfile(username,phone, profileImageUrl.toString())).addOnSuccessListener {
                Toast.makeText(context,"SUSCESSFULLY ADDED NAME!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener{
                Toast.makeText(context,"FAILADANA!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun selectImage() {
        val intent = Intent()
        intent.type="image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode==0 && resultCode == Activity.RESULT_OK){

            ImageUrl = data?.data!!
            circleImage.setImageURI(ImageUrl)
            binding.uploadImage.visibility = View.GONE
        }
    }
}