package ru.ytken.wildberries.internship.week5okhttpmoshi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import ru.ytken.wildberries.internship.week5okhttpmoshi.databinding.ActivityMainBinding
import java.io.IOException

class MainActivity : AppCompatActivity() {
    companion object {
        val url = "https://api.opendota.com"
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var moshiAdapter: JsonAdapter<List<CharacterModel>>
    private val baseUrl = "https://api.opendota.com/api/heroStats"

    private var listOfCharacters = MutableLiveData<List<CharacterModel>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.progressBarLoadCharacters.visibility = View.VISIBLE
        listOfCharacters.observe(this) {
            binding.progressBarLoadCharacters.visibility = View.INVISIBLE
            binding.recyclerViewListCharacters.apply {
                layoutManager = GridLayoutManager(this@MainActivity, 2)
                adapter = CharacterListAdapter(it) {
                    val intent = Intent(this@MainActivity, CharacterActivity::class.java)
                    intent.putExtra(CharacterActivity.tagCharacter, it)
                    startActivity(intent)
                }
            }
        }

        initMoshi()
        initOkHttp()
    }

    private fun initMoshi() {
        val moshi: Moshi = Moshi.Builder().build()
        val type = Types.newParameterizedType(List::class.java, CharacterModel::class.java)
        moshiAdapter = moshi.adapter(type)
    }

    private fun initOkHttp() {
        val request = Request.Builder()
            .url(baseUrl)
            .build()

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder().addInterceptor(logging).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onFailure(call: Call, e: IOException) {
                binding.progressBarLoadCharacters.visibility = View.INVISIBLE
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body?.string()
                if (json != null) {
                    Log.d("MainActivity", json)
                    listOfCharacters.postValue(moshiAdapter.fromJson(json))
                    Log.d("GETLIST", json)
                }

            }
        })
    }

}