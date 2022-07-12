package com.example.mybudget

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var transactions: List<Transaction>
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var linearlayoutManager:LinearLayoutManager
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transactions = arrayListOf()

        transactionAdapter = TransactionAdapter(transactions)
        linearlayoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "transactions").build()

        recyclerview.apply {
            adapter = transactionAdapter
            layoutManager = linearlayoutManager
        }


        addBtn.setOnClickListener {
            val intent = Intent(this,AddTransactionActivity::class.java)

            startActivity(intent)
        }
    }

    private fun fetchAll(){
        GlobalScope.launch {

            transactions = db.transactionDao().getAll()

            runOnUiThread {
                updateDashboard()
                transactionAdapter.setData(transactions)
            }
        }
    }
    private fun updateDashboard(){
        val totalAmount : Double = transactions.map { it.amount }.sum()
        val budgetAmount : Double = transactions.filter { it.amount>0 }.map { it.amount }.sum()
        val expenseAmount : Double = totalAmount-budgetAmount

        balance.text="₹%.2f".format(totalAmount)
        budget.text="₹%.1f".format(budgetAmount)
        expense.text="₹%.1f".format(expenseAmount)

    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}