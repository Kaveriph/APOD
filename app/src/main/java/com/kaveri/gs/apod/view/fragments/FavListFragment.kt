package com.kaveri.gs.apod.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.databinding.FragmentFavListBinding
import com.kaveri.gs.apod.model.pojo.APOD
import com.kaveri.gs.apod.view.adapter.FavListRecyclerViewAdapter
import com.kaveri.gs.apod.viewmodel.MainViewModel


/**
 * A simple [Fragment] subclass.
 * Use the [FavListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavListFragment : Fragment(), IListActionListener {

    private var recyclerViewAdapter: FavListRecyclerViewAdapter? = null
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
        binding?.lifecycleOwner = this
        init()
    }

    override fun onResume() {
        super.onResume()
        initObservers()
    }

    private fun initObservers() {
        viewModel.indexOfItemRemoved.observe(viewLifecycleOwner, {
            println("item removed : ${it}")
            if (it != -1) {
                listOfFavApod.removeAt(it)
                recyclerViewAdapter?.notifyItemRemoved(it)
            }
        })
        viewModel.favListOfAPOD.observe(viewLifecycleOwner, {
            println("fav list updated")
            listOfFavApod.clear()
            listOfFavApod.addAll(it)
            recyclerViewAdapter?.notifyDataSetChanged()
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.indexOfItemRemoved.value = -1
    }

    private fun init() {
        println("Creating fav list adapter")
        recyclerViewAdapter = FavListRecyclerViewAdapter(requireContext(), listOfFavApod, this)
        binding?.favListRv?.adapter = recyclerViewAdapter
        binding?.favListRv?.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getFavAPOD()
    }

    override fun removeItem(date: String) {
        viewModel.removeFromFav(date)
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