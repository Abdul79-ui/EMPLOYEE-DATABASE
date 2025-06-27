package com.example.employeedatabaseapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insert(employee: Employee)

    @Query("SELECT * FROM employees")
    fun getAllEmployees(): LiveData<List<Employee>>
}
