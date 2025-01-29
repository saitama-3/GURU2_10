package com.example.guru2_10

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DBManager(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS user_info (" +
                    "id TEXT PRIMARY KEY, " +
                    "password TEXT, " +
                    "points INTEGER DEFAULT 0)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS user_info")
        onCreate(db)
    }

    fun updateUserPoints(userId: String, points: Int) {
        val db: SQLiteDatabase = writableDatabase
        try {
            val exists = userExists(db, userId)
            Log.d("DBManager", "User exists: $exists")
            if (exists) {
                db.execSQL("UPDATE user_info SET points = points + ? WHERE id = ?", arrayOf(points, userId))
                Log.d("DBManager", "Update points : $userId = $points")
            } else {
                db.execSQL("INSERT INTO user_info (id, password, points) VALUES (?, ?, ?)", arrayOf(userId, "default_password", points))

                Log.d("DBManager", "new user: $userId with $points points")
            }
        } catch (e: Exception) {
            Log.e("DBManager", "Error updating points", e)
        } finally {
            db.close()
        }
    }

    @SuppressLint("Range")
    fun getUserPoints(userId: String): Int {
        val db: SQLiteDatabase = readableDatabase
        var points = 0
        try {
            val cursor = db.rawQuery("SELECT points FROM user_info WHERE id = ?", arrayOf(userId))
            Log.d("DBManager", "User ID: $userId, Cursor count: ${cursor.count}")
            if (cursor.moveToFirst()) {
                points = cursor.getInt(cursor.getColumnIndex("points"))
            }
            cursor.close()
        } catch (e: Exception) {
            Log.e("DBManager", "Error getting points", e)
        } finally {
            db.close()
        }
        return points
    }

    private fun userExists(db: SQLiteDatabase, userId: String): Boolean {
        var exists = false
        try {
            val cursor = db.rawQuery("SELECT id FROM user_info WHERE id = ?", arrayOf(userId))
            exists = cursor.moveToFirst()
            cursor.close()
        } catch (e: Exception) {
            Log.e("DBManager", "Error check user exists", e)
        }
        return exists
    }
}



