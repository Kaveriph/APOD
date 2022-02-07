package com.kaveri.gs.apod.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.databinding.FragmentFavListBinding
import com.kaveri.gs.apod.model.pojo.APOD
import com.kaveri.gs.apod.viewmodel.MainViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavListFragment : Fragment() {

    private lateinit var recyclerViewAdapter: FavListRecyclerViewAdapter
    private var binding: FragmentFavListBinding? = null
    private var listOfFavApod = ArrayList<APOD>()

    private val viewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(view)
        init()
        initObservers()
    }

    private fun initObservers() {
        viewModel.favListOfAPOD.observe(viewLifecycleOwner, {
            listOfFavApod.clear()
            listOfFavApod.addAll(it)
            recyclerViewAdapter.notifyDataSetChanged()
        })
    }

    private fun init() {
        viewModel.favListOfAPOD.value?.let {
            listOfFavApod.clear()
            listOfFavApod.addAll(it)
        }
        recyclerViewAdapter = FavListRecyclerViewAdapter(requireContext(), listOfFavApod)
        binding?.favListRv?.adapter = recyclerViewAdapter
        binding?.favListRv?.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getFavAPOD()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment FavListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = FavListFragment()
    }
}