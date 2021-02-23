package com.example.lab3.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lab3.R
import com.example.lab3.databinding.Task5Fragment3Binding

class Fragment3 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Task5Fragment3Binding.inflate(layoutInflater)

        binding.task5Fragment3ToFirstButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.third_to_first)
        }

        binding.task5Fragment3ToSecondButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.third_to_second)
        }

        return binding.root
    }
}