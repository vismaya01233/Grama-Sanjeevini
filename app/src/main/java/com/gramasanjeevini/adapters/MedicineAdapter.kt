package com.gramasanjeevini.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gramasanjeevini.R
import com.gramasanjeevini.repositories.MedicineSearchResult

class MedicineAdapter(
    private var items: List<MedicineSearchResult>,
    private val onItemClicked: (MedicineSearchResult) -> Unit
) : RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MedicineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_medicine, parent, false)
        return MedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: MedicineViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(nextItems: List<MedicineSearchResult>) {
        items = nextItems
        notifyDataSetChanged()
    }

    inner class MedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvMedicineName)
        private val details: TextView = itemView.findViewById(R.id.tvMedicineDetails)
        private val shop: TextView = itemView.findViewById(R.id.tvShopDetails)
        private val stock: TextView = itemView.findViewById(R.id.tvStockStatus)

        fun bind(result: MedicineSearchResult) {
            val medicine = result.medicine
            val context = itemView.context
            
            title.text = "${medicine.name} ${medicine.strength}"
            details.text = "${medicine.genericName} - Rs. ${medicine.priceRupees}"
            shop.text = "${result.shop.name}, ${result.shop.village} (${result.shop.distanceKm} km)"
            
            if (medicine.category == "Emergency") {
                itemView.background = ContextCompat.getDrawable(context, R.drawable.bg_card_alert)
                stock.setTextColor(ContextCompat.getColor(context, R.color.alert_red))
                stock.text = "⚠️ EMERGENCY STOCK: ${medicine.stockCount} left"
            } else {
                itemView.background = ContextCompat.getDrawable(context, R.drawable.bg_card)
                stock.setTextColor(ContextCompat.getColor(context, R.color.primary_green))
                stock.text = if (medicine.stockCount > 0) "${medicine.stockCount} available" else "Out of stock"
            }

            itemView.setOnClickListener { onItemClicked(result) }
        }
    }
}
