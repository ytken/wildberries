package ru.ytken.wildberries.internship.week2_1

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.fragment_2.*

class Fragment2 : Fragment(R.layout.fragment_2) {
    private val LOGTAG = "Week2_1" + Fragment2::class.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOGTAG, "onViewCreated")
        buttonPreviousFragment.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        buttonOpenAlertDialog.setOnClickListener {
            val builder = context?.let { it1 -> AlertDialog.Builder(it1) }
            builder?.setTitle("Custom AlertDialog")
                ?.setPositiveButton("Close",
                    DialogInterface.OnClickListener { dialog, i -> dialog.dismiss() })
            builder?.create()?.show()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(LOGTAG, "onAttach")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOGTAG, "onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(LOGTAG, "onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        Log.d(LOGTAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOGTAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(LOGTAG, "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(LOGTAG, "onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(LOGTAG, "onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(LOGTAG, "onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(LOGTAG, "onDetach")
    }
}