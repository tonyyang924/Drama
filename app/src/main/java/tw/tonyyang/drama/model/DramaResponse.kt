package tw.tonyyang.drama.model

import com.google.gson.annotations.SerializedName

class DramaResponse {
    @SerializedName("data")
    val data: List<Drama> = mutableListOf()
}