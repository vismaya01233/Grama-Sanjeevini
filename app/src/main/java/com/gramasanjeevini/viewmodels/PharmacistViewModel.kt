package com.gramasanjeevini.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramasanjeevini.models.Medicine
import com.gramasanjeevini.repositories.MedicineRepository
import kotlinx.coroutines.launch

class PharmacistViewModel(
    private val repository: MedicineRepository = MedicineRepository()
) : ViewModel() {

    private val _inventory = MutableLiveData<List<Medicine>>()
    val inventory: LiveData<List<Medicine>> = _inventory

    private val _lowStock = MutableLiveData<List<Medicine>>()
    val lowStock: LiveData<List<Medicine>> = _lowStock

    private val _expiringSoon = MutableLiveData<List<Medicine>>()
    val expiringSoon: LiveData<List<Medicine>> = _expiringSoon

    fun loadInventory(shopId: String) {
        viewModelScope.launch {
            _inventory.value = repository.getShopInventory(shopId)
        }
    }

    fun loadLowStock(shopId: String) {
        viewModelScope.launch {
            _lowStock.value = repository.getLowStockMedicines(shopId)
        }
    }

    fun loadExpiringSoon(shopId: String) {
        viewModelScope.launch {
            _expiringSoon.value = repository.getExpiringMedicines(shopId)
        }
    }
}
