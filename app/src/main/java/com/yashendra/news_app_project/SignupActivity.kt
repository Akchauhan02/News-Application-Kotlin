package com.yashendra.news_app_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.yashendra.news_app_project.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupBinding
    lateinit var mAuth : FirebaseAuth
    lateinit var mRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mRef = FirebaseDatabase.getInstance().reference

        mAuth = FirebaseAuth.getInstance()
        if(mAuth.currentUser != null)
        {
            val intent = Intent(this@SignupActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignupInSignup.setOnClickListener {

            val name = binding.nameInSignup.text.toString()
            val email = binding.emailInSignup.text.toString()
            val pass = binding.passwordInSignup.text.toString()

            signup(name,email,pass)

        }

        binding.txtLoginInSignup.setOnClickListener {

            val intent = Intent(this@SignupActivity,LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun signup(name : String,email: String, pass: String) {

        mAuth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    mRef.child("users").child(mAuth.currentUser?.uid!!).child("name").setValue(name)
                    mRef.child("users").child(mAuth.currentUser?.uid!!).child("email").setValue(email)

                    val intent = Intent(this@SignupActivity,MainActivity::class.java)
                    intent.putExtra("name",name)
                    startActivity(intent)
                    finish()

                } else {
                    Toast.makeText(this@SignupActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }


    }
}