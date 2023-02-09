package com.sultanseidov.genericelectricalenergycostcalculator.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.CustomerBillModel
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.base.Resource
import com.sultanseidov.genericelectricalenergycostcalculator.data.repository.ICustomerBillRepository
import com.sultanseidov.genericelectricalenergycostcalculator.util.Util.TAX_BRACKET_1
import com.sultanseidov.genericelectricalenergycostcalculator.util.Util.TAX_BRACKET_2
import com.sultanseidov.genericelectricalenergycostcalculator.util.Util.TAX_RATE_1
import com.sultanseidov.genericelectricalenergycostcalculator.util.Util.TAX_RATE_2
import com.sultanseidov.genericelectricalenergycostcalculator.util.Util.TAX_RATE_3
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ICustomerBillRepository
) : ViewModel() {

    private val _totalBillAmount = MutableLiveData<Resource<Int>>()
    val totalBillAmount: LiveData<Resource<Int>>
        get() = _totalBillAmount

    val customerBillsListFromDB = repository.getCustomerBills()

    fun deleteCustomerBill(customerBillModel: CustomerBillModel) = viewModelScope.launch {
        repository.deleteCustomerBill(customerBillModel)
    }

    fun insertCustomerBill(customerBillModel: CustomerBillModel) = viewModelScope.launch {
        repository.insertCustomerBill(customerBillModel)
    }

    fun getCustomerBillsListFromDdId(customerServiceNumber: String): LiveData<List<CustomerBillModel>> {
        return repository.getCustomerBillsByCSN(customerServiceNumber)
    }

    fun getCustomerElectricMetersByCSN(customerServiceNumber: String): LiveData<Int> {
        return repository.getCustomerElectricMetersByCSN(customerServiceNumber)
    }


    fun calculateElectricCost(
        currentReading: Int,
        previousMeter: Int
    ) {
        _totalBillAmount.value = Resource.loading(null)

        try {
            val consumption = currentReading - previousMeter
            var units = consumption
            var cost = 0

            if (units <= TAX_BRACKET_1) {
                cost += units * TAX_RATE_1
            } else {
                cost += TAX_BRACKET_1 * TAX_RATE_1
                units -= TAX_BRACKET_1

                if (units <= TAX_BRACKET_2) {
                    cost += units * TAX_RATE_2
                } else {
                    cost += TAX_BRACKET_2 * TAX_RATE_2
                    units -= TAX_BRACKET_2

                    cost += units * TAX_RATE_3
                }
            }

            Log.e("cost: ", cost.toString())
            _totalBillAmount.value = Resource.success(cost)

        } catch (e: Exception) {
            _totalBillAmount.value = Resource.error(e.toString(), null)
        }
    }


}