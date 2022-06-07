package ru.ytken.wildberries.internship.week6threadshandlers

import android.graphics.Color
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.ytken.wildberries.internship.week6threadshandlers.databinding.FragmentMainBinding
import java.math.BigDecimal
import java.math.MathContext
import kotlin.random.Random

class MainFragment: Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding

    private var piThreadLeibniz: Thread? = null
    private lateinit var incomingPiHandlerLeibniz: IncomingPiHandler

    private var piThreadChud: Thread? = null
    private lateinit var incomingPiHandlerChud: IncomingPiHandler

    private var timeThread: Thread? = null
    private lateinit var incomingTimeHandler: IncomingTimeHandler

    private lateinit var piFragment: PiFragment

    private var isInterrupted = false
    private var isRunning = true

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

        piFragment = PiFragment()
        activity?.supportFragmentManager!!.beginTransaction()
            .add(R.id.fragmentContainerFragment, piFragment)
            .commit()

        incomingPiHandlerLeibniz = IncomingPiHandler(this)
        incomingPiHandlerChud = IncomingPiHandler(this)
        incomingTimeHandler = IncomingTimeHandler(this)

        binding.buttonPlay.setOnClickListener {
            if ((piThreadLeibniz == null && piThreadChud == null) ||
                (piThreadLeibniz?.state == Thread.State.TERMINATED &&
                        piThreadChud?.state == Thread.State.TERMINATED)) {
                piThreadLeibniz = createPiThreadLeibniz()
                piThreadLeibniz?.start()

                piThreadChud = createPiThreadChud()
                piThreadChud?.start()

                timeThread = createTimeThread()
                timeThread?.start()

                incomingTimeHandler.sendMessage(Message().apply {
                    data = Bundle().apply {
                        putInt(IncomingTimeHandler.tagTimeValue, 0)
                    }
                })
            } else
                if (!isRunning)
                    isRunning = true
        }

        binding.buttonPause.setOnClickListener {
            if(piThreadLeibniz?.state == Thread.State.TIMED_WAITING) {
                Log.d("MainFragment", "Pause in state RUNNABLE")
                isRunning = false
            }
        }

        binding.buttonReset.setOnClickListener {
            isInterrupted = true

            piThreadLeibniz?.interrupt()
            piThreadChud?.interrupt()
            timeThread?.interrupt()

            isRunning = true
        }

    }

    fun setTimeOnTimer(curTime: Int) {
        val numberMins = (curTime / 60).toString().padStart(2, '0')
        val numberSecs = (curTime % 60).toString().padStart(2, '0')
        val textTime = "Время выполнения: $numberMins:$numberSecs"
        binding.textViewTime.text = textTime

        if (curTime % 20 == 0)
            binding.root.setBackgroundColor(Color.argb(120,
                Random.nextInt(0,256),
                Random.nextInt(0,256),
                Random.nextInt(0,256)
            ))
    }

    fun setPiValueLeibniz(curPiValue: String) = piFragment.updateTextViewLeibniz(curPiValue)
    fun setPiValueChud(curPiValue: String) = piFragment.updateTextViewChud(curPiValue)

    private fun createPiThreadLeibniz() = Thread(Runnable {
        var piValue = BigDecimal.ZERO
        var curLeibnizNumber = 1
        while (true) {
            try {
                Thread.sleep(15)
            } catch (e: InterruptedException) {
                if (isInterrupted) {
                    Thread.currentThread().interrupt()
                    break
                }
            }

            if(isRunning) {
                val curLeibnizNumberBig = BigDecimal.valueOf(curLeibnizNumber.toDouble())
                val curMultiplier = BigDecimal.valueOf(if((curLeibnizNumber - 1) % 4 == 0) 1 else -1)
                curLeibnizNumber += 2
                val curSummand = BigDecimal.ONE.divide(curLeibnizNumberBig, MathContext(1000))
                piValue = piValue.add(curSummand.multiply(curMultiplier).multiply(BigDecimal.valueOf(4.0)))

                val msg = Message().apply {
                    data = Bundle().apply {
                        putString(IncomingPiHandler.tagPiValueLeibniz, piValue.toPlainString())
                    }
                }
                incomingPiHandlerLeibniz.sendMessage(msg)
            }

        }
    })

    private fun createPiThreadChud() = Thread(Runnable {
        var piValueFast: BigDecimal = BigDecimal.ZERO
        var curK = 0
        while (true) {
            try {
                Thread.sleep(5)
            } catch (e: InterruptedException) {
                if (isInterrupted) {
                    Thread.currentThread().interrupt()
                    break
                }
            }

            if(isRunning) {
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

                val msg = Message().apply {
                    data = Bundle().apply {
                        putString(IncomingPiHandler.tagPiValueChud, finalPi.toPlainString())
                    }
                }
                incomingPiHandlerChud.sendMessage(msg)
            }

        }
    })

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

    private fun createTimeThread() = Thread(Runnable {
        var currentTime = 0
        while (true) {
            try {
                Thread.sleep(1000)
            } catch (e: InterruptedException) {
                if (isInterrupted) {
                    Thread.currentThread().interrupt()
                    binding.textViewTime.text = "Отмена"
                    break
                } else
                    binding.textViewTime.text = "Пауза"
            }

            if (isRunning) {
                currentTime += 1
                val msg = Message().apply {
                    data = Bundle().apply {
                        putInt(IncomingTimeHandler.tagTimeValue, currentTime)
                    }
                }
                incomingTimeHandler.sendMessage(msg)
            }
        }
    })

}