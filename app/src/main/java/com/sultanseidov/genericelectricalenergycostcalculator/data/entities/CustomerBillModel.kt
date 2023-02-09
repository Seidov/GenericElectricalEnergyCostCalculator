package com.sultanseidov.genericelectricalenergycostcalculator.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "CUSTOMER_BILLS")
data class CustomerBillModel(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "customer_service_number") val customerServiceNumber: String,
    @ColumnInfo(name = "customer_electric_meters") val customerElectricMeters: Int,
    @ColumnInfo(name = "customer_totalBill_amount") val customerTotalBillAmount: Int,
    @ColumnInfo(name = "customer_bill_date") val customerBillDate: Long
) {
    constructor(
        customerServiceNumber: String,
        customerElectricMeters: Int,
        customerTotalBillAmount: Int,
        customerBillDate: Long
    ) : this(
        0,
        customerServiceNumber,
        customerElectricMeters,
        customerTotalBillAmount,
        customerBillDate
    )
}