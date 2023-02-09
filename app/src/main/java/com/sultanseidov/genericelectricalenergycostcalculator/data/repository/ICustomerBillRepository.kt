package com.sultanseidov.genericelectricalenergycostcalculator.data.repository

import androidx.lifecycle.LiveData
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.CustomerBillModel
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.base.Resource

interface ICustomerBillRepository {

    fun getCustomerBills(): LiveData<List<CustomerBillModel>>

    fun getCustomerBillsByCSN(customerServiceNumber:String): LiveData<List<CustomerBillModel>>

    fun getCustomerElectricMetersByCSN(customerServiceNumber:String): LiveData<Int>

    suspend fun insertCustomerBill(customerBillModel: CustomerBillModel)

    suspend fun deleteCustomerBill(customerBillModel: CustomerBillModel)

}