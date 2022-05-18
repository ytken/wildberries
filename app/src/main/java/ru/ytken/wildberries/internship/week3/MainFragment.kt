package ru.ytken.wildberries.internship.week3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainFragment: Fragment(R.layout.activity_main) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonCalcLinear.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CalculatorLinearFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonCalcConstraint.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, CalculatorConstraintFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonFacebookConstraint.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FacebookConstraintFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonFacebookLinear.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, FacebookLinearFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonYandexConstraint.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MusicConstraintFragment())
                .addToBackStack(null)
                .commit()
        }

        buttonYandexLinear.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, MusicLinearFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}