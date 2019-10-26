package tw.tonyyang.drama.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import tw.tonyyang.drama.CoreApplication
import tw.tonyyang.drama.db.dao.DramaDao
import tw.tonyyang.drama.model.Drama

@Database(entities = [Drama::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dramaDao(): DramaDao

    companion object {
        private const val DB_NAME = "app.db"

        private val INSTANCE: AppDatabase by lazy {
            Room.databaseBuilder(
                CoreApplication.getContext(),
                AppDatabase::class.java,
                DB_NAME
            ).build()
        }

        fun getInstance() = INSTANCE
    }
}