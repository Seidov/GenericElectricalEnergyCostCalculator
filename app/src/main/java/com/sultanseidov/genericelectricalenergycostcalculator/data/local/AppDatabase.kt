package com.sultanseidov.genericelectricalenergycostcalculator.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sultanseidov.genericelectricalenergycostcalculator.data.entities.CustomerBillModel

@Database(entities = [CustomerBillModel::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun customerBillDao(): CustomerBillDao
}


