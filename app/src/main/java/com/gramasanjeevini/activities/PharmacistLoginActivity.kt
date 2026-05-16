package com.gramasanjeevini.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.gramasanjeevini.R

class PharmacistLoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist_login)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val tvSignupLink = findViewById<TextView>(R.id.tvSignupLink)

        btnLogin.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both phone and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Using the same email simulation as Signup (phone@gramasanjeevini.com)
            val email = "$phone@gramasanjeevini.com"

            btnLogin.isEnabled = false
            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val userId = result.user?.uid ?: ""
                    
                    // Fetch shop details from Firestore to get the shop name
                    db.collection("shops").document(userId).get()
                        .addOnSuccessListener { document ->
                            if (document.exists()) {
                                val shopName = document.getString("name") ?: "Pharmacy"
                                Toast.makeText(this, "Welcome back, $shopName!", Toast.LENGTH_SHORT).show()
                                
                                val intent = Intent(this, PharmacistDashboardActivity::class.java)
                                intent.putExtra(PharmacistDashboardActivity.EXTRA_SHOP_ID, userId)
                                intent.putExtra(PharmacistDashboardActivity.EXTRA_SHOP_NAME, shopName)
                                startActivity(intent)
                                finish()
                            } else {
                                btnLogin.isEnabled = true
                                Toast.makeText(this, "Shop details not found in database", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            btnLogin.isEnabled = true
                            Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    btnLogin.isEnabled = true
                    Toast.makeText(this, "Login failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        tvSignupLink.setOnClickListener {
            startActivity(Intent(this, PharmacistSignupActivity::class.java))
            finish()
        }
    }
}
