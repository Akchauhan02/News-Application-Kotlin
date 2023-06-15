package com.yashendra.news_app_project.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.yashendra.news_app_project.R

class ProfileFragment : Fragment() {

    lateinit var nameInProfile : TextView
    lateinit var emailInProfile : TextView
    lateinit var mref : DatabaseReference
    lateinit var mAuth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        mAuth = FirebaseAuth.getInstance()
        nameInProfile = root.findViewById(R.id.nameInProfile)
        emailInProfile = root.findViewById(R.id.emailInProfile)
        mref = FirebaseDatabase.getInstance().reference
        val currentId = mAuth.currentUser?.uid

        mref.child("users").child(currentId!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                nameInProfile.text = snapshot.child("name").getValue(String::class.java)
                emailInProfile.text = snapshot.child("email").getValue(String::class.java)

//                Toast.makeText(context, snapshot.child("name").getValue(String::class.java), Toast.LENGTH_SHORT).show()
//                Toast.makeText(context, "here", Toast.LENGTH_SHORT).show()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

        return root
    }


}