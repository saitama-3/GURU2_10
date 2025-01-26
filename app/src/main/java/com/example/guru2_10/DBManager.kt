package com.example.guru2_10

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
        val db = writableDatabase
        if (userExists(userId)) {
            db.execSQL("UPDATE user_info SET points = $points WHERE id = '$userId'")
        } else {
            db.execSQL("INSERT INTO user_info (id, points) VALUES ('$userId', $points)")
        }
    }

    @SuppressLint("Range")
    fun getUserPoints(userId: String): Int {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT points FROM user_info WHERE id = ?", arrayOf(userId))
        var points = 0
        if (cursor.moveToFirst()) {
            points = cursor.getInt(cursor.getColumnIndex("points"))
        }
        cursor.close()
        return points
    }

    @SuppressLint("Range")
    fun userExists(userId: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT id FROM user_info WHERE id = ?", arrayOf(userId))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
}