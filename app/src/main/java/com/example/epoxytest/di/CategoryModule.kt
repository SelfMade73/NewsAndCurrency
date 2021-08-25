package com.example.epoxytest.di

import com.example.epoxytest.db.AppDatabase
import com.example.epoxytest.db.CategoryDao
import com.example.epoxytest.repositories.CategoryRepository
import com.example.epoxytest.repositories.CategoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CategoryModule {
    @Provides
    fun provideCategoryRepository(
        categoryDao: CategoryDao
    ): CategoryRepository = CategoryRepositoryImpl(categoryDao)

    @Provides
    fun provideCategoryDao( database: AppDatabase) = database.categoryDao()
}