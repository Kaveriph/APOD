package com.kaveri.gs.apod.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.view.activity.MainActivity
import com.kaveri.gs.apod.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [APODFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class APODFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_a_p_o_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        viewModel.getAPOD()
    }

    companion object {
        fun newInstance() = APODFragment()
    }
}