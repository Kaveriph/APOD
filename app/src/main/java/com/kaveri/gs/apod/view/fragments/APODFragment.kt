package com.kaveri.gs.apod.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.databinding.FragmentAPODBinding
import com.kaveri.gs.apod.view.activity.MainActivity
import com.kaveri.gs.apod.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [APODFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class APODFragment : Fragment() , APODFragmentActionListener {

    private var binding: FragmentAPODBinding? = null

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        println("onCreateView")
        return inflater.inflate(R.layout.fragment_a_p_o_d, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("onViewCreated")
        init(view)
        initObservers()
    }

    private fun initObservers() {
        viewModel.selectedDate.observe(viewLifecycleOwner, {
            viewModel.getAPOD()
        })
    }

    private fun init(view: View) {
        println("onInit")
        binding = DataBindingUtil.bind(view)
        binding?.viewModel = viewModel
        binding?.actionListener = this
        binding?.setLifecycleOwner(requireActivity())
    }

    companion object {
        fun newInstance() = APODFragment()
    }

    override fun onDateSelectionClick() {
        viewModel.openDatePicker(this.parentFragmentManager)
    }
}