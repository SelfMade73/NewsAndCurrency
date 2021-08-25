package com.example.epoxytest.repositories

import com.example.epoxytest.models.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    val allCategory : Flow<List<Category>>
    //fun loadCategory() : Flow<List<Category>>

    suspend fun saveCategories( categories  : List<Category>)

}