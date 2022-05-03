package ru.ytken.wildberries.internship.week1.content

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_content_provider.*
import kotlinx.android.synthetic.main.item_resume_list.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import ru.ytken.wildberries.internship.week1.R

class ContentProviderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_provider)

        setSupportActionBar(custom_toolbar_content_provider as Toolbar?)
        imageButtonToolbarClose.setOnClickListener { finish() }

        val helper = MyHelper(applicationContext)
        val db = helper.readableDatabase
        val rs = db.rawQuery("SELECT * FROM RESUMETABLE", null)
        if (rs.moveToFirst())
            for (i in 0 until rs.count) {
                val view = View.inflate(ContentProviderActivity@this, R.layout.item_resume_list, null)
                view.textViewHeadingItem.text = rs.getString(1)
                view.textViewDescriptionItem.text = rs.getString(2)
                linearLayoutResumeList.addView(view)
                rs.moveToNext()
            }

    }

}