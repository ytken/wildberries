package ru.ytken.wildberries.internship.week6flow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.ytken.wildberries.internship.week6flow.databinding.FragmentPiBinding
import java.math.BigDecimal
import java.math.MathContext
import java.math.RoundingMode
import kotlin.coroutines.coroutineContext
import kotlin.random.Random

class PiFragment: Fragment(R.layout.fragment_pi) {
    private lateinit var binding: FragmentPiBinding

    private var piJobLeibniz: Job? = null
    private var piJobChud: Job? = null

    private var piValue: BigDecimal = BigDecimal.valueOf(0)
    private var curLeibnizNumber = 1

    private var piValueFast: BigDecimal = BigDecimal.valueOf(0)
    private var curK = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPiBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val piFlowLeibniz: Flow<BigDecimal> = flow {
        while (true) {
            val curLeibnizNumberBig = BigDecimal.valueOf(curLeibnizNumber.toDouble())
            val curMultiplier = BigDecimal.valueOf(if ((curLeibnizNumber-1)%4 == 0) 1 else -1)
            curLeibnizNumber += 2
            val curSummand = BigDecimal.ONE.divide(curLeibnizNumberBig, MathContext(1000))
            piValue = piValue.add(curSummand.multiply(curMultiplier).multiply(BigDecimal.valueOf(4.0)))
            delay(10)
            emit(piValue)
        }
    }

    private val piFlowChud: Flow<BigDecimal> = flow {
        while (true) {
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
            emit(finalPi)
            }
    }.flowOn(Dispatchers.IO)

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

    fun startPiCounting() {
        if ((piJobLeibniz == null && piJobChud == null) || (piJobLeibniz!!.isCancelled && piJobChud!!.isCancelled)) {
            piJobLeibniz = viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    piFlowLeibniz.cancellable().collect {
                        binding.textViewPiLeibniz.text = it.toPlainString()
                    }
                }
            }

            piJobChud = viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    piFlowChud.cancellable().collect {
                        binding.textViewPiChud.text = it.toPlainString()
                    }
                }
            }
        }
    }

    fun pausePiCounting() {
        if (piJobLeibniz?.isCancelled == false && piJobChud?.isCancelled == false) {
            piJobLeibniz?.cancel()
            piJobChud?.cancel()
        }
    }

    fun cancelPiCounting() {
        if (piJobLeibniz?.isCancelled == false && piJobChud?.isCancelled == false) {
            piJobLeibniz?.cancel()
            piJobChud?.cancel()
        }

        initAllValues()
    }

    private fun initAllValues() {
        piValue = BigDecimal.ZERO
        curLeibnizNumber = 1

        piValueFast = BigDecimal.ZERO
        curK = 0
    }
}