package com.gramasanjeevini.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.gramasanjeevini.R
import com.gramasanjeevini.models.Medicine

class ShopAdapter(
    private var items: List<Medicine>
) : RecyclerView.Adapter<ShopAdapter.ShopMedicineViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopMedicineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_inventory, parent, false)
        return ShopMedicineViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopMedicineViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun updateData(newItems: List<Medicine>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ShopMedicineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.tvInventoryName)
        private val details: TextView = itemView.findViewById(R.id.tvInventoryDetails)
        private val status: TextView = itemView.findViewById(R.id.tvInventoryStatus)

        fun bind(medicine: Medicine) {
            val context = itemView.context
            title.text = "${medicine.name} ${medicine.strength}"
            details.text = "${medicine.category} - expires ${medicine.expiryDate}"
            
            if (medicine.category == "Emergency" || medicine.isLowStock) {
                itemView.background = ContextCompat.getDrawable(context, R.drawable.bg_card_alert)
                status.setTextColor(ContextCompat.getColor(context, R.color.alert_red))
                val prefix = if (medicine.category == "Emergency") "⚠️ EMERGENCY: " else "⚠️ LOW STOCK: "
                status.text = "$prefix${medicine.stockCount} | Rs. ${medicine.priceRupees}"
            } else {
                itemView.background = ContextCompat.getDrawable(context, R.drawable.bg_card)
                status.setTextColor(ContextCompat.getColor(context, R.color.primary_green))
                status.text = "Stock: ${medicine.stockCount} | Rs. ${medicine.priceRupees}"
            }
        }
    }
}
