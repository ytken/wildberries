package ru.ytken.wildberries.internship.week1.content

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri

class ResumeContentProvider: ContentProvider() {

    companion object {
        val TABLE_NAME = "RESUMETABLE"

        val PROVIDER_NAME = "ru.ytken.wildberries.internship.week1/ResumeContentProvider"
        val URL = "content://$PROVIDER_NAME/$TABLE_NAME"
        val CONTENT_URI = Uri.parse(URL)

        val _ID = "_id"
        val JOB = "JOB"
        val DESCRIPTION = "DESCRIPTION"
    }

    lateinit var db: SQLiteDatabase

    override fun onCreate(): Boolean {
        val helper = context?.let { MyHelper(it) }
        return if (helper != null) {
            db = helper.writableDatabase
            db != null
        } else false
    }

    override fun query(
        uri: Uri,
        columns: Array<out String>?,
        cond: String?,
        cond_val: Array<out String>?,
        order: String?
    ): Cursor? {
        return db.query(TABLE_NAME, columns, cond, cond_val, null, null, order)
    }

    override fun getType(p0: Uri): String? {
        return "vnd.android.cursor.dir/vnd.example.resumetable"
    }

    override fun insert(uri: Uri, cv: ContentValues?): Uri? {
        db.insert(TABLE_NAME, null, cv)
        context?.contentResolver?.notifyChange(uri, null)
        return uri
    }

    override fun delete(uri: Uri, cond: String?, cond_val: Array<out String>?): Int {
        val c = db.delete(TABLE_NAME, cond, cond_val)
        context?.contentResolver?.notifyChange(uri, null)
        return c
    }

    override fun update(uri: Uri, cv: ContentValues?, cond: String?, cond_val: Array<out String>?): Int {
        val c = db.update(TABLE_NAME, cv, cond, cond_val)
        context?.contentResolver?.notifyChange(uri, null)
        return c
    }
}