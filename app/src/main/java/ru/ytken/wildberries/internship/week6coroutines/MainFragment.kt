package ru.ytken.wildberries.internship.week6coroutines

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.*
import ru.ytken.wildberries.internship.week6coroutines.databinding.FragmentMainBinding
import java.math.BigDecimal
import java.math.MathContext
import kotlin.random.Random

class MainFragment: Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    private var piJobLeibniz: Job? = null
    private var piJobChud: Job? = null
    private var timeJob: Job? = null

    var piValue: BigDecimal = BigDecimal.ZERO
    var curLeibnizNumber = 1

    var piValueFast: BigDecimal = BigDecimal.ZERO
    var curK = 0

    var currentSeconds = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment = PiFragment()
        activity?.supportFragmentManager!!.beginTransaction()
            .add(R.id.fragmentContainerFragment, fragment)
            .commit()

        binding.buttonPlay.setOnClickListener {
            if ((piJobLeibniz == null && piJobChud == null) || (piJobLeibniz!!.isCancelled && piJobChud!!.isCancelled)) {
                lifecycleScope.launch {
                    piJobLeibniz = countPiLeibniz {
                        fragment.updateLeibnizTextView(it)
                    }
                    piJobLeibniz

                    piJobChud = countPiChud {
                        fragment.updateChudTextView(it)
                    }
                    piJobChud

                    timeJob = countTime {
                        val numberMins = (it/60).toString().padStart(2, '0')
                        val numberSecs = (it%60).toString().padStart(2, '0')
                        val textTime = "Время выполнения: $numberMins:$numberSecs"
                        binding.textViewTime.text = textTime

                        if (it % 20 == 0)
                            binding.root.setBackgroundColor(
                                Color.argb(120,
                                Random.nextInt(0,256),
                                Random.nextInt(0,256),
                                Random.nextInt(0,256)
                            ))
                    }
                    timeJob
                }
            }
        }

        binding.buttonPause.setOnClickListener {
            piJobLeibniz?.cancel()
            piJobChud?.cancel()
            timeJob?.cancel()
        }

        binding.buttonReset.setOnClickListener {
            piJobLeibniz?.cancel()
            piJobChud?.cancel()
            timeJob?.cancelTimeJob()
            initVars()
        }
    }

    private fun initVars() {
        piValue = BigDecimal.ZERO
        curLeibnizNumber = 1

        piValueFast = BigDecimal.ZERO
        curK = 0

        currentSeconds = 0
    }

    private suspend fun countPiLeibniz(updateResult: (String) -> Unit): Job = lifecycleScope.launch {
        while (isActive) {
            delay(10)
            val curLeibnizNumberBig = BigDecimal.valueOf(curLeibnizNumber.toDouble())
            val curMultiplier = BigDecimal.valueOf(if ((curLeibnizNumber-1)%4 == 0) 1 else -1)
            curLeibnizNumber += 2
            val curSummand = BigDecimal.ONE.divide(curLeibnizNumberBig, MathContext(1000))
            piValue = piValue.add(curSummand.multiply(curMultiplier).multiply(BigDecimal.valueOf(4.0)))
            updateResult(piValue.toPlainString())
        }
    }

    private suspend fun countPiChud(updateResult: (String) -> Unit): Job = lifecycleScope.launch {
        while (isActive) {
            delay(10)
            val bigCurK = BigDecimal.valueOf(curK.toLong())
            val curTopValue =
                BigDecimal.valueOf(if (curK % 2 == 0) 1 else -1)
                    .multiply((BigDecimal.valueOf(6.0).multiply(bigCurK)).factorial())
                    .multiply(BigDecimal.valueOf(13591409).add(bigCurK.multiply(BigDecimal.valueOf(545140134))))
            val curBottomValue =
                ((bigCurK.multiply(BigDecimal.valueOf(3.0))).factorial())
                    .multiply(bigCurK.factorial().pow(3))
                    .multiply((BigDecimal.valueOf(640320).pow(6*curK+3)).sqrt(100))
            piValueFast = piValueFast.add(curTopValue.divide(curBottomValue, MathContext(1000)).multiply(
                BigDecimal.valueOf(12)))
            curK += 1
            val finalPi = BigDecimal.ONE.divide(piValueFast, MathContext(1000))

            updateResult(finalPi.toPlainString())
        }
    }

    private fun BigDecimal.factorial(): BigDecimal {
        var result = BigDecimal.ONE
        for (i in 1..this.intValueExact())
            result = result.multiply(BigDecimal.valueOf(i.toLong()))
        return result
    }

    private fun BigDecimal.sqrt(precision: Int): BigDecimal {
        var x = BigDecimal.valueOf(1000.0)
        for (i in 0..precision)
            x = x.minus((x.pow(2).minus(this)).divide(BigDecimal.valueOf(2).multiply(x), MathContext(precision)))
        return x
    }

    private suspend fun countTime(updateTime: (Int) -> Unit): Job = lifecycleScope.launch {
        updateTime(currentSeconds)
        while (isActive) {
            delay(1000)
            currentSeconds += 1
            updateTime(currentSeconds)
        }
    }

    private fun Job.cancelTimeJob() {
        this.cancel()
        binding.textViewTime.text = "Отмена"
    }

}