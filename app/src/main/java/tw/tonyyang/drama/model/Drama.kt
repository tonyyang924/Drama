package tw.tonyyang.drama.model

import com.google.gson.annotations.SerializedName

data class Drama(
    @SerializedName("drama_id")
    val dramaId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("total_views")
    val totalViews: Int,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("thumb")
    val thumb: String,
    @SerializedName("rating")
    val rating: Double
)