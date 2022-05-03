package ru.ytken.wildberries.internship.week1.content

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.ytken.wildberries.internship.week1.R

class MyHelper(context: Context):
    SQLiteOpenHelper(context,
        context.getString(R.string.db_name),
        null,
        1) {

    val mContext = context

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE RESUMETABLE" +
                    "(_id INTEGER PRIMARY KEY AUTOINCREMENT, JOB TEXT, DESCRIPTION TEXT)")
        db?.execSQL("INSERT INTO RESUMETABLE(JOB,DESCRIPTION) VALUES" +
                "('Переворачиватель пингвинов','Не боюсь морозов')," +
                "('Программист Android','Трудностей тоже не боюсь')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

}