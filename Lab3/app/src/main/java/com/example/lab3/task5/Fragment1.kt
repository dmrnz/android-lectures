package com.example.lab3.task5

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.lab3.R
import com.example.lab3.databinding.Task5Fragment1Binding

class Fragment1 : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = Task5Fragment1Binding.inflate(layoutInflater)

        binding.task5Fragment1ToSecondButton.setOnClickListener {
            view?.findNavController()?.navigate(R.id.first_to_second)
        }

        return binding.root
    }
}