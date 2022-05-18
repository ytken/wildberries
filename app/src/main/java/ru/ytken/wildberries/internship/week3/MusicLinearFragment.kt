package ru.ytken.wildberries.internship.week3

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.music_constraint_fragment.*
import kotlinx.android.synthetic.main.music_linear_fragment.*

class MusicLinearFragment: Fragment(R.layout.music_linear_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewLinear.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewLinear.adapter = MusicAdapter()
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerViewLinear)
    }

}