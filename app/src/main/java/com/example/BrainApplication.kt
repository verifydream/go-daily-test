package com.example

import android.app.Application
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.AppRepository
import com.example.data.MasterDataEntity
import com.example.data.SettingsEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class BrainApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "daily_life_second_brain_database"
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    val repository by lazy {
        AppRepository(database)
    }

    override fun onCreate() {
        super.onCreate()
        // Pre-populate data if it's empty
        applicationScope.launch {
            repository.prepopulateIfNeeded()
            // Let's add some default master data if none exists
            val currentData = repository.masterData.firstOrNull()
            if (currentData.isNullOrEmpty()) {
                val defaults = listOf(
                    MasterDataEntity(type = "CATEGORY_FINANCE", name = "Food & Dining"),
                    MasterDataEntity(type = "CATEGORY_FINANCE", name = "Salary"),
                    MasterDataEntity(type = "CATEGORY_FINANCE", name = "Transportation"),
                    MasterDataEntity(type = "CATEGORY_FINANCE", name = "Entertainment"),
                    MasterDataEntity(type = "CATEGORY_FINANCE", name = "Utilities"),
                    MasterDataEntity(type = "ASSET", name = "Cash Wallet"),
                    MasterDataEntity(type = "ASSET", name = "Bank Checking"),
                    MasterDataEntity(type = "ASSET", name = "Credit Card Account"),
                    MasterDataEntity(type = "SOURCE_INCOME", name = "Primary Job"),
                    MasterDataEntity(type = "SOURCE_INCOME", name = "Freelance Consulting"),
                    MasterDataEntity(type = "SOURCE_INCOME", name = "Investment Interest")
                )
                defaults.forEach { repository.addMasterData(it) }
            }
        }
    }
}
