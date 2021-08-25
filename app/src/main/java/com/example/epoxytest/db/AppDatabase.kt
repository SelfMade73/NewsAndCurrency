package com.example.epoxytest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

import com.example.epoxytest.models.Category
import com.example.epoxytest.models.CategoryTypes
import com.example.epoxytest.models.CurrencyItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Category::class,CurrencyItem::class], version = 1, exportSchema = false)
abstract class AppDatabase  : RoomDatabase() {


    abstract fun categoryDao() : CategoryDao
    abstract fun ratesDao() : RatesDao



    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }


        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "finance_app_db"
            )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        GlobalScope.launch(Dispatchers.IO) {
                            getInstance(context).categoryDao().insert(
                                CategoryTypes.values().asList().map { Category(it.category) }
                            )
                        }
                    }
                })
                .build()

    }

}