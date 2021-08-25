package com.example.epoxytest.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsItem(
    val title : String,
    val description: String,
    val url : String?,
    @SerializedName("urlToImage")
    val imgUrl : String?,
    @SerializedName("publishedAt")
    val date : String,
    val content : String?,
    val author : String?
) : Parcelable{
    fun getTrimmedTitle() = if (title.length > 100 ) title.take(100) + "..." else title
    fun getTrimmedAuthor() : String {
        author?.let{
            return if ( it.length > 80) it.take(80)  + "..." else author
        }
        return String()
    }
}
