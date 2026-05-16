package com.gramasanjeevini.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gramasanjeevini.R
import com.gramasanjeevini.models.Medicine
import com.gramasanjeevini.repositories.MedicineRepository
import kotlinx.coroutines.launch

class AddMedicineActivity : AppCompatActivity() {

    private val repository = MedicineRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_medicine)

        val shopId = intent.getStringExtra(EXTRA_SHOP_ID) ?: ""

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val etName = findViewById<EditText>(R.id.etMedName)
        val etGeneric = findViewById<EditText>(R.id.etGenericName)
        val etCategory = findViewById<EditText>(R.id.etCategory)
        val etStrength = findViewById<EditText>(R.id.etStrength)
        val etPrice = findViewById<EditText>(R.id.etPrice)
        val etStock = findViewById<EditText>(R.id.etStock)
        val etExpiry = findViewById<EditText>(R.id.etExpiry)
        val btnAdd = findViewById<Button>(R.id.btnAddMedicine)

        btnAdd.setOnClickListener {
            val name = etName.text.toString().trim()
            val generic = etGeneric.text.toString().trim()
            val category = etCategory.text.toString().trim()
            val strength = etStrength.text.toString().trim()
            val priceStr = etPrice.text.toString().trim()
            val stockStr = etStock.text.toString().trim()
            val expiry = etExpiry.text.toString().trim()

            if (name.isEmpty() || category.isEmpty() || priceStr.isEmpty() || stockStr.isEmpty() || expiry.isEmpty()) {
                Toast.makeText(this, "Please fill required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val medicine = Medicine(
                name = name,
                genericName = generic,
                category = category,
                strength = strength,
                priceRupees = priceStr.toDoubleOrNull() ?: 0.0,
                stockCount = stockStr.toIntOrNull() ?: 0,
                expiryDate = expiry,
                shopId = shopId
            )

            btnAdd.isEnabled = false
            lifecycleScope.launch {
                val success = repository.addMedicine(medicine)
                if (success) {
                    Toast.makeText(this@AddMedicineActivity, "Medicine Added!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    btnAdd.isEnabled = true
                    Toast.makeText(this@AddMedicineActivity, "Failed to add medicine", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    companion object {
        const val EXTRA_SHOP_ID = "extra_shop_id"
    }
}
