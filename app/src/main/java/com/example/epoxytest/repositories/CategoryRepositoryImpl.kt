package com.example.epoxytest.repositories

import com.example.epoxytest.db.CategoryDao
import com.example.epoxytest.models.Category
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
): CategoryRepository {
    override  val allCategory : Flow<List<Category>> = categoryDao.getCurrentCategories().flowOn(Dispatchers.IO)
    override suspend fun saveCategories(categories: List<Category>) {
        categoryDao.insert(categories)
    }
}