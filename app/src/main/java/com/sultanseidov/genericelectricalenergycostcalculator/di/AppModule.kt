package com.sultanseidov.genericelectricalenergycostcalculator.di

import android.content.Context
import androidx.room.Room
import com.sultanseidov.genericelectricalenergycostcalculator.data.local.AppDatabase
import com.sultanseidov.genericelectricalenergycostcalculator.data.local.CustomerBillDao
import com.sultanseidov.genericelectricalenergycostcalculator.data.repository.CustomerBillRepository
import com.sultanseidov.genericelectricalenergycostcalculator.data.repository.ICustomerBillRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, "CostCalculatorApp"
    ).build()


    @Singleton
    @Provides
    fun injectCustomerBillDao(database: AppDatabase) = database.customerBillDao()

    @Singleton
    @Provides
    fun injectNormalRepo(customerBill: CustomerBillDao) =
        CustomerBillRepository(customerBill) as ICustomerBillRepository
}