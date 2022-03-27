package com.example.shortmovieapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.example.shortmovieapp.R
import com.example.shortmovieapp.databinding.ActivityRegisterBinding
import com.example.shortmovieapp.databinding.FragmentDetailMovieBinding
import com.example.shortmovieapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    private var _binding : FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var database: DatabaseReference
    private lateinit var auth : FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        _binding = FragmentProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // firebase instance connection
        database = FirebaseDatabase.getInstance().getReference("users")
        auth = FirebaseAuth.getInstance()

        val uid = auth.currentUser?.uid
        val ordersRef = database.child("$uid")

        // Get contact data from Firebase and equals to ui components
        var getData = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                // Check user information is empty or not
                if (snapshot.exists()){

                    var username = snapshot.child("username").getValue()
                    binding.usernameeProfile.setText(username.toString())

                    var phone = snapshot.child("phone").getValue()
                    binding.phoneProfile.setText(phone.toString())
                    var url = snapshot.child("imageUrl").getValue()

                    // For profile picture
                    context?.let {
                        Glide.with(this@ProfileFragment)
                            .load(url)
                            .into(circle_image_profile)
                        binding.userProfilePicture.visibility = View.INVISIBLE
                    }
                }else{
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setPositiveButton("Tamam"){_, _ ->
                    }
                    builder.setNegativeButton(""){_, _ ->}
                    builder.setTitle("Lütfen Bilgilerinizi Database'e Kaydedin")
                    builder.setMessage("Bunun İçin Güncelleme Paneline Git Butonuna Tıklayın")
                    builder.create().show()
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        }
        ordersRef.addValueEventListener(getData)
        ordersRef.addListenerForSingleValueEvent(getData)

        binding.goesToProfileUpdateFragment.setOnClickListener {
            goToProfileUpdateFragment(it)
        }
    }

    private fun goToProfileUpdateFragment(it:View) {
        val navigationController = Navigation.findNavController(it)
        navigationController.navigate(R.id.action_profileFragment2_to_updateProfileFragment)
    }
}