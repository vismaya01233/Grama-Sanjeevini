package com.gramasanjeevini.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gramasanjeevini.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnSearchMedicine).setOnClickListener {
            startActivity(Intent(this, SearchMedicineActivity::class.java))
        }
        
        findViewById<Button>(R.id.btnViewMap).setOnClickListener {
            startActivity(Intent(this, MapActivity::class.java))
        }

        findViewById<Button>(R.id.btnEmergencyStock).setOnClickListener {
            startActivity(Intent(this, EmergencyStockActivity::class.java))
        }

        findViewById<Button>(R.id.btnPharmacistLogin).setOnClickListener {

            // Point to Signup first as requested
            startActivity(Intent(this, PharmacistSignupActivity::class.java))
        }
    }
}
