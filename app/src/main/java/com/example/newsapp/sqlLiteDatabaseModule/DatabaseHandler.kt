package com.example.newsapp.sqlLiteDatabaseModule

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.newsapp.model.ArticlesItem

class DatabaseHandler(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "NewsDatabase"
        private val TABLE_NEWS = "newsTable"
        private val KEY_ID = "id"
        private val KEY_DESCRIPTION = "desc"
        private val KEY_TITLE = "title"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NEWS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_DESCRIPTION + " TEXT,"
                + KEY_TITLE + " TEXT" + ")")
        p0?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS)
        onCreate(p0)
    }

    //method to insert data
    fun addNews(emp: ArticlesItem):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.source?.id)
        contentValues.put(KEY_DESCRIPTION, emp.description) // EmpModelClass Name
        contentValues.put(KEY_TITLE,emp.title ) // EmpModelClass Phone
        // Inserting Row
        val success = db.insert(TABLE_NEWS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    @SuppressLint("Range")
    fun showNews():ArrayList<ArticlesItem>{
        val empList:ArrayList<ArticlesItem> = ArrayList<ArticlesItem>()
        val selectQuery = "SELECT  * FROM $TABLE_NEWS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var strId: Int
        var strDescription: String
        var strTitle: String
        if (cursor.moveToFirst()) {
            do {
                strId = cursor.getInt(cursor.getColumnIndex("id"))
                strDescription = cursor.getString(cursor.getColumnIndex("desc"))
                strTitle = cursor.getString(cursor.getColumnIndex("title"))
                val emp= ArticlesItem()
               emp.description = strDescription
               emp.source?.id = strId.toString()
               emp.title = strTitle
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

}