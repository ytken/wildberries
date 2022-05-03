package ru.ytken.wildberries.internship.week1.content

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.activity_content_provider.*
import kotlinx.android.synthetic.main.activity_content_resolver.*
import kotlinx.android.synthetic.main.item_resume_list.view.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import ru.ytken.wildberries.internship.week1.R

class ContentResolverActivity : AppCompatActivity() {

    val TAG = ContentResolverActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_resolver)

        setSupportActionBar(custom_toolbar_content_resolver as Toolbar?)
        imageButtonToolbarClose.setOnClickListener { finish() }

        buttonClearImportedList.setOnClickListener { linearLayoutImportedResume.removeAllViews() }

        val cursor = contentResolver.query(
            ResumeContentProvider.CONTENT_URI,
            arrayOf(ResumeContentProvider.JOB, ResumeContentProvider.DESCRIPTION),
            null,
            null,
            null
        )

        buttonImportResumeFromHH.setOnClickListener {
            linearLayoutImportedResume.removeAllViews()
            if (cursor != null && cursor.count > 0) {
                cursor.moveToFirst()
                for (i in 0 until cursor.count) {
                    val view = createResumeItemView(cursor.getString(0), cursor.getString(1))
                    linearLayoutImportedResume.addView(view)
                    cursor.moveToNext()
                }
            }
        }
    }

    private fun createResumeItemView(heading: String, description: String): View {
        val view = View.inflate(ContentProviderActivity@this, R.layout.item_resume_list, null)
        view.textViewHeadingItem.text = heading
        view.textViewDescriptionItem.text = description
        view.setBackgroundColor(getColor(R.color.teal_200))
        return view
    }
}