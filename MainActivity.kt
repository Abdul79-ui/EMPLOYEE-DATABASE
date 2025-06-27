package com.example.employeedatabaseapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.example.employeedatabaseapp.data.AppDatabase
import com.example.employeedatabaseapp.data.Employee
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = AppDatabase.getDatabase(this)

        val nameInput = findViewById<EditText>(R.id.editName)
        val positionInput = findViewById<EditText>(R.id.editPosition)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val employeeList = findViewById<ListView>(R.id.employeeList)

        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, ArrayList())
        employeeList.adapter = adapter

        // Observe employee list
        db.employeeDao().getAllEmployees().observe(this) { employees ->
            adapter.clear()
            adapter.addAll(employees.map { "${it.name} - ${it.position}" })
        }

        // Insert employee
        btnAdd.setOnClickListener {
            val name = nameInput.text.toString()
            val position = positionInput.text.toString()
            if (name.isNotBlank() && position.isNotBlank()) {
                lifecycleScope.launch {
                    db.employeeDao().insert(Employee(name = name, position = position))
                    nameInput.text.clear()
                    positionInput.text.clear()
                }
            }
        }
    }
}
