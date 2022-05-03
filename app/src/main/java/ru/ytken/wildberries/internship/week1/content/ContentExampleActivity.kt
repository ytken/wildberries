package ru.ytken.wildberries.internship.week1.content

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_content_example.*
import kotlinx.android.synthetic.main.toolbar_custom.*
import ru.ytken.wildberries.internship.week1.R

// Класс демонстрирует использование ContentProvider и ContentResolver в приложении
// ContentProviderActivity используется для вывода данных, хранящихся в локальной БД SQLite (класс MyHelper).
// ResumeContentProvider является наследником ContentProvider для предоставления доступа к данным БД
//          локального хранилища этого приложения. Данные могут быть получены извне при условии
//          известного URI.
// ContentResolverActivity используется для доступа к данным через ContentProvider. Такая функциональность
//          была использована для удобства, вообще ContentProvider нужно использовать ТОЛЬКО для связи
//          между разными приложениями, внутри одного приложения это не очень разумно.
// Пример был основан на используемой связи между сторонним приложением по поиску работы и самым
//          популярным сервисом: hh.ru. Это облегчает размещение своего резюме в любом приложении,
//          использующем такую функциональность.

class ContentExampleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content_example)

        setSupportActionBar(custom_toolbar_content_example as Toolbar?)
        imageButtonToolbarClose.setOnClickListener { finish() }

        buttonHhRu.setOnClickListener {
            startActivity(Intent(this, ContentProviderActivity::class.java))
        }

        buttonSuperhhRu.setOnClickListener {
            startActivity(Intent(this, ContentResolverActivity::class.java))
        }
    }
}