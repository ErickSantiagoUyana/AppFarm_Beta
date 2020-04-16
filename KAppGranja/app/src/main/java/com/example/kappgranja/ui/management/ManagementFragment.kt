package com.example.kappgranja.ui.management

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.kappgranja.R

class ManagementFragment : Fragment() {




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_management, container, false)

        view.findViewById<Button>(R.id.button_sheeps).setOnClickListener {
            findNavController().navigate(R.id.action_managementFragment_to_sheepsFragment)
        }
        view.findViewById<Button>(R.id.button_goats).setOnClickListener {
            findNavController().navigate(R.id.action_managementFragment_to_goatFragment)
        }
        view.findViewById<Button>(R.id.button_pigs).setOnClickListener {
            findNavController().navigate(R.id.action_managementFragment_to_pigsFragment)
        }
        view.findViewById<Button>(R.id.button_cows).setOnClickListener {
            findNavController().navigate(R.id.action_managementFragment_to_cowsFragment)
        }
        return view
    }

}



