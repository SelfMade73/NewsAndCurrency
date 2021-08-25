package com.example.epoxytest.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.epoxytest.db.CategoryDao_Impl
import kotlinx.android.parcel.Parcelize


enum class CategoryTypes(val category : String, var checked : Boolean = true ){
    BUSINESS("business", false),
    ENTERTAINMENT("entertainment"),
    GENERAL("general"),
    HEALTH("health"),
    SCIENCE("science"),
    SPORTS("sports"),
    TECHNOLOGY("technology")
}

@Entity
@Parcelize
data class Category (
    @PrimaryKey
    val category : String,
    var checked : Boolean = true ) : Parcelable{
    override fun equals(other: Any?): Boolean {
        if ( other is Category){
            return category == other.category
        }
        return super.equals(other)
    }
}



class Categories(
    val categories : List<Category>
){
    override fun equals(other: Any?): Boolean {
        if ( other is Categories ){
            return this.categories == other.categories
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return categories.hashCode()
    }
}