package ru.ytken.wildberries.internship.wildberriesextrasort

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.math.roundToInt
import kotlin.random.Random
import kotlin.system.measureTimeMillis

suspend fun generateRandomArray(size: String, maxValue: String): IntArray? =
    GlobalScope.async {
    return@async if (size.isNotEmpty())
       IntArray(size.toInt()) {
            val numberMax = if (maxValue.isNotEmpty())
                maxValue.toInt()
            else 20
            Random.nextInt(-numberMax, numberMax)
        }
    else null
}.await()

suspend fun IntArray.sortByDefault(): Long =
    withContext(Dispatchers.Default) {
        measureTimeMillis { this@sortByDefault.sort() }
    }

fun IntArray.swap(i: Int, j: Int) {
    val temp = this[i]
    this[i] = this[j]
    this[j] = temp
}

// Сортировка пузырьком
// Медленно работает на тестах, в которых маленькие элементы стоят в конце массива
// Худшее время: O(n^2)
// Среднее время: O(n^2)
// Лучшее время: O(n)
// Затраты памяти: O(1)
suspend fun IntArray.sortBubbleType(): Long =
    withContext(Dispatchers.Default) {
        measureTimeMillis {
            var swap = true
            val a = this@sortBubbleType
            while(swap){
                swap = false
                for(i in 0 until a.size-1){
                    if(a[i] > a[i+1]){
                        a.swap(i,i+1)
                        swap = true
                    }
                }
            }
        }
    }

// Сортировка расческой
// Идея состоит в том, чтобы «устранить» элементы с небольшими значения в конце массива, которые замедляют работу алгоритма.
// Худшее время: O(n^2)
// Среднее время: Ω(n^2 / 2^p), p - количество инкрементов
// Лучшее время: O(n*log(n))
// Затраты памяти: O(1)
suspend fun IntArray.sortCombType(): Long =
    withContext(Dispatchers.Default) {
        measureTimeMillis {
            val factor = 1.247
            val a = this@sortCombType
            var gapFactor = a.size / factor
            while (gapFactor > 1) {
                val gap = gapFactor.roundToInt()
                var i = 0
                for (j in gap until a.size) {
                    if (a[i] > a[j])
                        a.swap(i,j)
                    i += 1
                }
                gapFactor /= factor
            }
        }
    }

// Сортировка выбором
// В подмножестве массива находится максимум (или минимум). Затем выбранное значение меняют местами со значением первого неотсортированного элемента.
// Худшее время: O(n^2)
// Среднее время: O(n^2)
// Лучшее время: O(n^2)
// Затраты памяти: O(n)
suspend fun IntArray.sortSelectType(): Long =
    withContext(Dispatchers.Default) {
        measureTimeMillis {
            val a = this@sortSelectType
            for(i in a.indices) {
                var min = i
                for (j in i until a.size)
                    if (a[j] < a[min])
                        min = j
                a.swap(i,min)
            }
        }
    }

// Сортировка вставками
// Каждый последующий элемент массива размещается так, чтобы он оказался между ближайшими элементами с минимальным и максимальным значением.
// Худшее время: O(n^2)
// Среднее время: O(n^2)
// Лучшее время: O(n)
// Затраты памяти: O(n)
suspend fun IntArray.sortInsertType(): Long =
    withContext(Dispatchers.Default) {
        measureTimeMillis {
            val a = this@sortInsertType
            for (i in 1 until a.size) {
                val x = a[i]
                var j = i
                while((j > 0) && (a[j-1] > x)) {
                    a[j] = a[j-1]
                    j -= 1
                }
                a[j] = x
            }
        }
    }