package com.sultanseidov.genericelectricalenergycostcalculator.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.CustomerBillModel
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.base.Resource

@Dao
interface CustomerBillDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCustomerBill(customerBillModel: CustomerBillModel)

    @Delete
    suspend fun deleteCustomerBill(customerBillModel: CustomerBillModel)

    @Query("SELECT * FROM CUSTOMER_BILLS")
    fun observeCustomerBills(): LiveData<List<CustomerBillModel>>

    @Query("SELECT * FROM CUSTOMER_BILLS WHERE customer_service_number = :customerServiceNumber ORDER BY customer_bill_date DESC LIMIT 3")
    fun observeCustomerBillsByCSN(customerServiceNumber: String): LiveData<List<CustomerBillModel>>

    @Query("SELECT customer_electric_meters FROM CUSTOMER_BILLS WHERE customer_service_number = :customerServiceNumber ORDER BY customer_bill_date DESC LIMIT 1")
    fun observeCustomerElectricMetersByCSN(customerServiceNumber: String): LiveData<Int>
}