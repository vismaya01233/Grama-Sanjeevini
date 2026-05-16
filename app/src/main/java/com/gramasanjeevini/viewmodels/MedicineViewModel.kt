package com.gramasanjeevini.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gramasanjeevini.models.PharmacyShop
import com.gramasanjeevini.repositories.MedicineRepository
import com.gramasanjeevini.repositories.MedicineSearchResult
import kotlinx.coroutines.launch

class MedicineViewModel(
    private val repository: MedicineRepository = MedicineRepository()
) : ViewModel() {

    private val _searchResults = MutableLiveData<List<MedicineSearchResult>>()
    val searchResults: LiveData<List<MedicineSearchResult>> = _searchResults

    private val _allShops = MutableLiveData<List<PharmacyShop>>()
    val allShops: LiveData<List<PharmacyShop>> = _allShops

    fun search(query: String) {
        viewModelScope.launch {
            val results = repository.searchMedicines(query)
            _searchResults.value = results
        }
    }

    fun loadEmergencyStock() {
        viewModelScope.launch {
            val results = repository.getEmergencyStock()
            _searchResults.value = results
        }
    }

    fun loadAllShops() {
        viewModelScope.launch {
            val shops = repository.getAllShops()
            _allShops.value = shops
        }
    }
}
