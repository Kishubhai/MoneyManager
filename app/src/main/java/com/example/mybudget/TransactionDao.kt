package com.example.mybudget

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TransactionDao {
    @Query("SELECT * from transactions ")
    fun getAll(): List<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg transaction: Transaction)

    @Delete
    fun delete(transaction: Transaction)

    @Update
    fun update(vararg transaction: Transaction)
}