package com.kaveri.gs.apod.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.databinding.ActivityMainBinding
import com.kaveri.gs.apod.view.fragments.APODFragment
import com.kaveri.gs.apod.view.fragments.FavListFragment
import com.kaveri.gs.apod.view.fragments.apodFragmentList
import com.kaveri.gs.apod.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.mBottomNavigationBar

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
        initObservers()
    }

    private fun initObservers() {
        viewModel.selectedDate.observe(this, {

        })
    }

    fun init() {
        binding.viewModel = this.viewModel
        viewModel.init()
        binding.setLifecycleOwner(this@MainActivity)
        val apodFragment = APODFragment.newInstance()
        val listFragment = apodFragmentList.newInstance(3)
        val favListFragmnet = FavListFragment.newInstance()
        setCurrentFragment(apodFragment)
        mBottomNavigationBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.apod -> {
                    setCurrentFragment(apodFragment)
                } R.id.favorites -> {
                    setCurrentFragment(favListFragmnet)
                } else -> {
                    setCurrentFragment(listFragment)
                }
            }
            true
        }
    }

    fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mFrameLayout, fragment)
                .commit()
        }
    }
}