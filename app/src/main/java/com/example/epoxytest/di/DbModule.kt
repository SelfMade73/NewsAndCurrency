package com.example.epoxytest.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.epoxytest.db.AppDatabase
import com.example.epoxytest.db.CategoryDao
import com.example.epoxytest.db.PrePopulateCallback
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ) : AppDatabase = AppDatabase.getInstance(context)


}