package com.example.epoxytest.db

import androidx.room.*
import com.example.epoxytest.models.Category
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category ORDER BY checked DESC")
    fun getCurrentCategories(): Flow<List<Category>>

    @Insert( onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert ( categories : List<Category>)

    @Update
    suspend fun update ( categories: List<Category> )
}