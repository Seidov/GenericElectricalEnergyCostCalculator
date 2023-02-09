package com.sultanseidov.genericelectricalenergycostcalculator.data.repository

import androidx.lifecycle.LiveData
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.CustomerBillModel
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.base.Resource
import com.sultanseidov.genericelectricalenergycostcalculator.data.local.CustomerBillDao
import javax.inject.Inject

class CustomerBillRepository @Inject constructor(
    private val customerBillDao: CustomerBillDao
) : ICustomerBillRepository {

    override fun getCustomerBills(): LiveData<List<CustomerBillModel>> {
        return customerBillDao.observeCustomerBills()
    }

    override fun getCustomerBillsByCSN(customerServiceNumber: String): LiveData<List<CustomerBillModel>> {
        return customerBillDao.observeCustomerBillsByCSN(customerServiceNumber)
    }

    override fun getCustomerElectricMetersByCSN(customerServiceNumber: String): LiveData<Int> {
        return customerBillDao.observeCustomerElectricMetersByCSN(customerServiceNumber)
    }

    override suspend fun insertCustomerBill(customerBillModel: CustomerBillModel) {
        customerBillDao.insertCustomerBill(customerBillModel)
    }

    override suspend fun deleteCustomerBill(customerBillModel: CustomerBillModel) {
        customerBillDao.deleteCustomerBill(customerBillModel)
    }
}