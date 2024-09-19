import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "UserDatabase.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_USERS_TABLE = """
            CREATE TABLE users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                age INTEGER
            )
        """
        db.execSQL(CREATE_USERS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun insertUser(name: String, age: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("age", age)
        }
        db.insert("users", null, contentValues)
        db.close()
    }

    fun getAllUsers(): Cursor {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM users", null)
    }

    fun updateUser(id: Int, name: String, age: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("name", name)
            put("age", age)
        }
        db.update("users", contentValues, "id=?", arrayOf(id.toString()))
        db.close()
    }

    fun deleteUser(id: Int) {
        val db = this.writableDatabase
        db.delete("users", "id=?", arrayOf(id.toString()))
        db.close()
    }
}
