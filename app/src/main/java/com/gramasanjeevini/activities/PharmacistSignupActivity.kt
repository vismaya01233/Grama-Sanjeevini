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

class PharmacistSignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist_signup)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val etShopName = findViewById<EditText>(R.id.etShopName)
        val etOwnerName = findViewById<EditText>(R.id.etOwnerName)
        val etVillage = findViewById<EditText>(R.id.etVillage)
        val etPhone = findViewById<EditText>(R.id.etPhone)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLoginLink = findViewById<TextView>(R.id.tvLoginLink)

        btnRegister.setOnClickListener {
            val name = etShopName.text.toString().trim()
            val owner = etOwnerName.text.toString().trim()
            val village = etVillage.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || owner.isEmpty() || village.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Using phone number as email for Firebase Auth simulation (phone@grama.com)
            val email = "$phone@gramasanjeevini.com"

            btnRegister.isEnabled = false
            Toast.makeText(this, "Registering...", Toast.LENGTH_SHORT).show()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener { result ->
                    val userId = result.user?.uid ?: ""
                    val shopData = hashMapOf(
                        "id" to userId,
                        "name" to name,
                        "ownerName" to owner,
                        "village" to village,
                        "phoneNumber" to phone,
                        "isOpen" to true,
                        "latitude" to 12.9716, // Default coordinates, can be updated later
                        "longitude" to 77.5946
                    )

                    db.collection("shops").document(userId)
                        .set(shopData)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Shop Registered Successfully!", Toast.LENGTH_LONG).show()
                            startActivity(Intent(this, PharmacistLoginActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            btnRegister.isEnabled = true
                            Toast.makeText(this, "Error saving shop: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
                .addOnFailureListener { e ->
                    btnRegister.isEnabled = true
                    Toast.makeText(this, "Registration failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }

        tvLoginLink.setOnClickListener {
            startActivity(Intent(this, PharmacistLoginActivity::class.java))
            finish()
        }
    }
}
