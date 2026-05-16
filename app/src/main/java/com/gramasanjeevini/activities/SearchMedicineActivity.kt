package com.gramasanjeevini.activities

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gramasanjeevini.R
import com.gramasanjeevini.adapters.MedicineAdapter
import com.gramasanjeevini.repositories.MedicineSearchResult
import com.gramasanjeevini.viewmodels.MedicineViewModel

class SearchMedicineActivity : AppCompatActivity() {
    private val viewModel: MedicineViewModel by viewModels()
    private lateinit var adapter: MedicineAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_medicine)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val list = findViewById<RecyclerView>(R.id.rvMedicines)
        list.layoutManager = LinearLayoutManager(this)
        
        adapter = MedicineAdapter(emptyList()) { showMedicineDetails(it) }
        list.adapter = adapter

        viewModel.searchResults.observe(this) { results ->
            adapter.submitList(results)
        }

        // Initial search to load all medicines
        viewModel.search("")

        findViewById<EditText>(R.id.etMedicineSearch).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.search(s?.toString() ?: "")
            }
            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun showMedicineDetails(result: MedicineSearchResult) {
        AlertDialog.Builder(this)
            .setTitle(result.medicine.name)
            .setMessage(
                "${result.medicine.genericName} ${result.medicine.strength}\n" +
                    "Available at ${result.shop.name}, ${result.shop.village}\n" +
                    "Stock: ${result.medicine.stockCount}\n" +
                    "Price: Rs. ${result.medicine.priceRupees}"
            )
            .setPositiveButton("Call shop") { _, _ ->
                startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:${result.shop.phoneNumber}")))
            }
            .setNeutralButton("View on Map") { _, _ ->
                val intent = Intent(this, MapActivity::class.java).apply {
                    putExtra(MapActivity.EXTRA_LATITUDE, result.shop.latitude)
                    putExtra(MapActivity.EXTRA_LONGITUDE, result.shop.longitude)
                    putExtra(MapActivity.EXTRA_TITLE, result.shop.name)
                }
                startActivity(intent)
            }
            .setNegativeButton("Close", null)
            .show()
    }
}
