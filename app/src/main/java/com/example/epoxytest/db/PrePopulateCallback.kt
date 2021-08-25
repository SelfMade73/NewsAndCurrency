package com.example.epoxytest.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.epoxytest.models.Category
import com.example.epoxytest.models.CategoryTypes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class PrePopulateCallback @Inject constructor(
    private val categoryDao: CategoryDao
) : RoomDatabase.Callback(){
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        GlobalScope.launch (Dispatchers.IO ){
            categoryDao.insert(
                CategoryTypes.values().asList().map {
                    Category(it.category )
                }
            )
        }
    }
}