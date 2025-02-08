package com.bahar.loginwithgoogle

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class HomeActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val welcomeTextView = findViewById<TextView>(R.id.tv_welcome)
        val emailTextView = findViewById<TextView>(R.id.tv_email)
        val profileImageView = findViewById<ImageView>(R.id.iv_profile)
        val signOutButton = findViewById<Button>(R.id.btn_google_sign_out_card)

        val userName = intent.getStringExtra("USER_NAME") ?: "User"
        val userEmail = intent.getStringExtra("USER_EMAIL") ?: "No Email"
        val userPhoto = intent.getStringExtra("USER_PHOTO")

        welcomeTextView.text = "Welcome, $userName!"
        emailTextView.text = "Email: $userEmail"

        // Load profile image using Picasso
        if (!userPhoto.isNullOrEmpty()) {
            Picasso.get().load(userPhoto).into(profileImageView)
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        signOutButton.setOnClickListener {
            googleSignInClient.signOut().addOnCompleteListener(this) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
