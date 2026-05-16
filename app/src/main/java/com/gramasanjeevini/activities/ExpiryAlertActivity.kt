package com.gramasanjeevini.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gramasanjeevini.R
import com.gramasanjeevini.adapters.ShopAdapter
import com.gramasanjeevini.viewmodels.PharmacistViewModel

class ExpiryAlertActivity : AppCompatActivity() {
    private val viewModel: PharmacistViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expiry_alert)

        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        val shopId = intent.getStringExtra(PharmacistDashboardActivity.EXTRA_SHOP_ID).orEmpty()
        val list = findViewById<RecyclerView>(R.id.rvExpiryAlerts)
        list.layoutManager = LinearLayoutManager(this)
        
        val adapter = ShopAdapter(emptyList())
        list.adapter = adapter

        viewModel.expiringSoon.observe(this) { medicines ->
            adapter.updateData(medicines)
        }

        viewModel.loadExpiringSoon(shopId)
    }
}
