package tw.tonyyang.drama.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val TABLE_NAME = "drama"
const val DRAMA_ID = "drama_id"
const val NAME = "name"
const val TOTAL_VIEWS = "total_views"
const val CREATED_AT = "created_at"
const val THUMB = "thumb"
const val RATING = "rating"

@Entity(tableName = TABLE_NAME)
data class Drama(
    @PrimaryKey
    @ColumnInfo(name = DRAMA_ID)
    @SerializedName(DRAMA_ID)
    val dramaId: Int,
    @ColumnInfo(name = NAME)
    @SerializedName(NAME)
    val name: String,
    @ColumnInfo(name = TOTAL_VIEWS)
    @SerializedName(TOTAL_VIEWS)
    val totalViews: Int,
    @ColumnInfo(name = CREATED_AT)
    @SerializedName(CREATED_AT)
    val createdAt: String,
    @ColumnInfo(name = THUMB)
    @SerializedName(THUMB)
    val thumb: String,
    @ColumnInfo(name = RATING)
    @SerializedName(RATING)
    val rating: Double
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(dramaId)
        parcel.writeString(name)
        parcel.writeInt(totalViews)
        parcel.writeString(createdAt)
        parcel.writeString(thumb)
        parcel.writeDouble(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Drama> {
        override fun createFromParcel(parcel: Parcel): Drama {
            return Drama(parcel)
        }

        override fun newArray(size: Int): Array<Drama?> {
            return arrayOfNulls(size)
        }
    }

}