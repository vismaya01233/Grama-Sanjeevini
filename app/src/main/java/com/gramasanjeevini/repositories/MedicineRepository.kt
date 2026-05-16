package com.gramasanjeevini.repositories

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.gramasanjeevini.models.Medicine
import com.gramasanjeevini.models.PharmacyShop
import kotlinx.coroutines.tasks.await

class MedicineRepository {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getAllShops(): List<PharmacyShop> {
        return try {
            val snapshot = db.collection("shops").get().await()
            snapshot.toObjects(PharmacyShop::class.java)
        } catch (e: Exception) {
            Log.e("FirestoreError", "Error fetching shops: ${e.message}")
            emptyList()
        }
    }

    suspend fun searchMedicines(query: String): List<MedicineSearchResult> {
        val allMedicines = try {
            val snapshot = db.collection("medicines").get().await()
            snapshot.toObjects(Medicine::class.java)
        } catch (e: Exception) {
            Log.e("FirestoreError", "Error fetching medicines: ${e.message}")
            emptyList()
        }

        val allShops = getAllShops()
        val shopMap = allShops.associateBy { it.id }

        if (allMedicines.isEmpty()) {
            Log.d("FirestoreDebug", "No medicines found in database")
        }

        val normalized = query.trim().lowercase()
        
        return allMedicines
            .filter {
                normalized.isEmpty() || 
                it.name.lowercase().contains(normalized) ||
                it.genericName.lowercase().contains(normalized) ||
                it.category.lowercase().contains(normalized)
            }
            .mapNotNull { medicine ->
                val shop = shopMap[medicine.shopId]
                if (shop != null) {
                    MedicineSearchResult(medicine, shop)
                } else {
                    Log.d("FirestoreDebug", "Medicine ${medicine.name} skipped: shopId ${medicine.shopId} not found")
                    null
                }
            }
            .sortedWith(compareBy({ !it.shop.isOpen }, { it.shop.distanceKm }))
    }

    suspend fun getEmergencyStock(): List<MedicineSearchResult> {
        return searchMedicines("").filter { 
            it.medicine.category.equals("Emergency", ignoreCase = true) || 
            it.medicine.name.contains("Insulin", ignoreCase = true) 
        }
    }

    suspend fun getLowStockMedicines(shopId: String): List<Medicine> {
        return getShopInventory(shopId).filter { it.isLowStock }
    }

    suspend fun getExpiringMedicines(shopId: String): List<Medicine> {
        return getShopInventory(shopId).filter { it.expiryDate <= "2026-12-31" }
    }

    suspend fun getShopInventory(shopId: String): List<Medicine> {
        return try {
            db.collection("medicines")
                .whereEqualTo("shopId", shopId)
                .get()
                .await()
                .toObjects(Medicine::class.java)
        } catch (e: Exception) {
            Log.e("FirestoreError", "Error fetching inventory: ${e.message}")
            emptyList()
        }
    }

    suspend fun addMedicine(medicine: Medicine): Boolean {
        return try {
            val docRef = db.collection("medicines").document()
            val medicineWithId = medicine.copy(id = docRef.id)
            docRef.set(medicineWithId).await()
            true
        } catch (e: Exception) {
            Log.e("FirestoreError", "Error adding medicine: ${e.message}")
            false
        }
    }
}

data class MedicineSearchResult(
    val medicine: Medicine,
    val shop: PharmacyShop
)
