package ru.ytken.wildberries.internship.week6flow

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.ytken.wildberries.internship.week6flow.databinding.FragmentMainBinding
import kotlin.random.Random

class MainFragment: Fragment(R.layout.fragment_main) {
    private lateinit var binding: FragmentMainBinding
    private val livePiValue = MutableLiveData<String>()

    private var timeValue = 0
    private var timeJob: Job? = null
    private val timeFlow: Flow<Int> = flow {
        emit(timeValue)
        while(true) {
            delay(1000)
            timeValue += 1
            emit(timeValue)
        }
    }.flowOn(Dispatchers.IO)

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
            fragment.startPiCounting()

            if (timeJob == null || timeJob!!.isCancelled) {
                timeJob = viewLifecycleOwner.lifecycleScope.launch {
                    repeatOnLifecycle(Lifecycle.State.STARTED) {
                        timeFlow.cancellable().collect {
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
                    }
                }
            }
        }

        binding.buttonPause.setOnClickListener {
            fragment.pausePiCounting()
            if (timeJob?.isCancelled == false)
                timeJob?.cancel()
        }

        binding.buttonReset.setOnClickListener {
            fragment.cancelPiCounting()
            binding.textViewTime.text = "Отмена"
            if (timeJob?.isCancelled == false)
                timeJob?.cancel()
            timeValue = 0
        }
    }
}