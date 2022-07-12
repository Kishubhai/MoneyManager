package com.example.mybudget

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Transaction::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun transactionDao() : TransactionDao

           }
