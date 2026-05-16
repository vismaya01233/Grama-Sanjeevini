package com.gramasanjeevini.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gramasanjeevini.R
import com.gramasanjeevini.adapters.ShopAdapter
import com.gramasanjeevini.viewmodels.PharmacistViewModel

class PharmacistDashboardActivity : AppCompatActivity() {
    private val viewModel: PharmacistViewModel by viewModels()
    private lateinit var shopId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pharmacist_dashboard)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        shopId = intent.getStringExtra(EXTRA_SHOP_ID).orEmpty()
        val shopName = intent.getStringExtra(EXTRA_SHOP_NAME) ?: "Pharmacy Dashboard"
        findViewById<TextView>(R.id.tvDashboardTitle).text = shopName

        val summaryText = findViewById<TextView>(R.id.tvDashboardSummary)
        val list = findViewById<RecyclerView>(R.id.rvInventory)
        list.layoutManager = LinearLayoutManager(this)
        
        val adapter = ShopAdapter(emptyList())
        list.adapter = adapter

        viewModel.inventory.observe(this) { medicines ->
            adapter.updateData(medicines)
            updateSummary(summaryText, medicines.size, viewModel.lowStock.value?.size ?: 0)
        }

        viewModel.lowStock.observe(this) { lowStockList ->
            updateSummary(summaryText, viewModel.inventory.value?.size ?: 0, lowStockList.size)
        }

        // Load data
        viewModel.loadInventory(shopId)
        viewModel.loadLowStock(shopId)

        findViewById<Button>(R.id.btnExpiryAlerts).setOnClickListener {
            startActivity(Intent(this, ExpiryAlertActivity::class.java).putExtra(EXTRA_SHOP_ID, shopId))
        }

        findViewById<Button>(R.id.btnAddMedicine).setOnClickListener {
            val intent = Intent(this, AddMedicineActivity::class.java)
            intent.putExtra(AddMedicineActivity.EXTRA_SHOP_ID, shopId)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when returning from AddMedicineActivity
        viewModel.loadInventory(shopId)
        viewModel.loadLowStock(shopId)
    }

    private fun updateSummary(textView: TextView, total: Int, lowStock: Int) {
        textView.text = "$total medicines | $lowStock low-stock alerts"
    }

    companion object {
        const val EXTRA_SHOP_ID = "extra_shop_id"
        const val EXTRA_SHOP_NAME = "extra_shop_name"
    }
}
