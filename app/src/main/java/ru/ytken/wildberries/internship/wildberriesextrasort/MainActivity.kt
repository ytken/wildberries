package ru.ytken.wildberries.internship.wildberriesextrasort

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.math.max
import kotlin.random.Random
import kotlin.system.measureTimeMillis

// Программа создана для испытания различных алгоритмов сортировки и состоит из одного экрана.
// Для начала необходимо сгенерировать массив, для этого нужно задать количество элементов в массиве (обязательно)
//          и максимум значения элемента по модулю (по желанию, по дефолту подставляется 20). После указания необходимых
//          параметров нужно нажать кнопку "СГЕНЕРИРОВАТЬ МАССИВ". Часть полученного массива появится в поле под кнопкой.
// Далее по нажатию на кнопки производите сортировку массива нужным методом. Время, затраченное на сортировку появится
//          в поле внизу экрана.

class MainActivity : AppCompatActivity() {
    val TAG_ARRAY = "TAG_ARRAY"
    var array: IntArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        array = savedInstanceState?.getIntArray(TAG_ARRAY)
        if (array != null)
            textViewRandomArray.text = array!!.copyOfRange(0, 200)
                .joinToString(
                    separator = " ", postfix = "",
                    prefix = "   Исходный массив (первые 200): "
                )

        buttonGenerateArray.setOnClickListener {
            lifecycleScope.launch {
                onStartSorting()
                val numberElements = editTextNumberElements.text.toString()
                val maxElement = editTextMaxElement.text.toString()
                if (numberElements.isNotEmpty()) {
                    array = generateRandomArray(
                        numberElements, maxElement
                    )
                    textViewRandomArray.text = array!!.copyOfRange(0, 200)
                        .joinToString(
                            separator = " ", postfix = "",
                            prefix = "   Исходный массив (первые 200): "
                        )
                }
                else
                    Toast.makeText(applicationContext, "Введите количество элементов", Toast.LENGTH_SHORT).show()


                onFinishSorting()
            }
        }

        buttonDefaultSort.setOnClickListener {
            lifecycleScope.launch {
                onStartSorting()
                val arrayToSort = array?.copyOf()
                if (arrayToSort != null) {
                    val timeInMillis = arrayToSort.sortByDefault()

                    textViewSortArray.text = arrayToSort.copyOfRange(0, 200)
                        .joinToString(
                        separator = " ", postfix = "",
                        prefix = " Отсортированный массив (первые 200): "
                    )
                    textViewTime.text = "${timeInMillis.toString()} мc"
                } else
                    Toast.makeText(applicationContext, "Сгенерируйте массив", Toast.LENGTH_SHORT).show()
                onFinishSorting()
            }
        }

        buttonBubbleSort.setOnClickListener {
            lifecycleScope.launch {
                onStartSorting()
                val arrayToSort = array?.copyOf()
                if (arrayToSort != null) {
                    val timeInMillis = arrayToSort.sortBubbleType()

                    textViewSortArray.text = arrayToSort.copyOfRange(0, 200)
                        .joinToString(
                            separator = " ", postfix = "",
                            prefix = " Отсортированный массив (первые 200): "
                        )
                    textViewTime.text = "${timeInMillis.toString()} мc"
                } else
                    Toast.makeText(applicationContext, "Сгенерируйте массив", Toast.LENGTH_SHORT).show()
                onFinishSorting()
            }
        }

        buttonCombSort.setOnClickListener {
            lifecycleScope.launch {
                onStartSorting()
                val arrayToSort = array?.copyOf()
                if (arrayToSort != null) {
                    val timeInMillis = arrayToSort.sortCombType()

                    textViewSortArray.text = arrayToSort.copyOfRange(0, 200)
                        .joinToString(
                            separator = " ", postfix = "",
                            prefix = " Отсортированный массив (первые 200): "
                        )
                    textViewTime.text = "${timeInMillis.toString()} мc"
                } else
                    Toast.makeText(applicationContext, "Сгенерируйте массив", Toast.LENGTH_SHORT).show()
                onFinishSorting()
            }
        }

        buttonSelectSort.setOnClickListener {
            lifecycleScope.launch {
                onStartSorting()
                val arrayToSort = array?.copyOf()
                if (arrayToSort != null) {
                    val timeInMillis = arrayToSort.sortSelectType()

                    textViewSortArray.text = arrayToSort.copyOfRange(0, 200)
                        .joinToString(
                            separator = " ", postfix = "",
                            prefix = " Отсортированный массив (первые 200): "
                        )
                    textViewTime.text = "${timeInMillis.toString()} мc"
                } else
                    Toast.makeText(applicationContext, "Сгенерируйте массив", Toast.LENGTH_SHORT).show()
                onFinishSorting()
            }
        }

        buttonInsertSort.setOnClickListener {
            lifecycleScope.launch {
                onStartSorting()
                val arrayToSort = array?.copyOf()
                if (arrayToSort != null) {
                    val timeInMillis = arrayToSort.sortInsertType()

                    textViewSortArray.text = arrayToSort.copyOfRange(0, 200)
                        .joinToString(
                            separator = " ", postfix = "",
                            prefix = " Отсортированный массив (первые 200): "
                        )
                    textViewTime.text = "${timeInMillis.toString()} мc"
                } else
                    Toast.makeText(applicationContext, "Сгенерируйте массив", Toast.LENGTH_SHORT).show()
                onFinishSorting()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putIntArray(TAG_ARRAY, array)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    private fun onStartSorting() {
        linearLayoutSort.visibility = View.VISIBLE
        linearLayoutButtons.visibility = View.INVISIBLE
        textViewTime.text = getString(R.string.text_default_time)
    }

    private fun onFinishSorting() {
        linearLayoutSort.visibility = View.GONE
        linearLayoutButtons.visibility = View.VISIBLE
    }

}