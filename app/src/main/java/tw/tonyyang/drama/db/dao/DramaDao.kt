package tw.tonyyang.drama.db.dao

import androidx.room.*
import tw.tonyyang.drama.model.Drama
import tw.tonyyang.drama.model.TABLE_NAME

@Dao
interface DramaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(drama: Drama)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg dramas: Drama)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(dramas: List<Drama>)

    @Update
    fun update(drama: Drama)

    @Update
    fun update(vararg dramas: Drama)

    @Delete
    fun delete(drama: Drama)

    @Query("DELETE FROM $TABLE_NAME")
    fun deleteAll()

    @Query("SELECT * from $TABLE_NAME")
    fun getAllDramas(): List<Drama>
}