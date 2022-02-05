package com.kaveri.gs.apod.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kaveri.gs.apod.R
import com.kaveri.gs.apod.view.fragments.APODFragment
import com.kaveri.gs.apod.view.fragments.apodFragmentList
import com.kaveri.gs.apod.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.mBottomNavigationBar

class MainActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        val apodFragment = APODFragment.newInstance()
        val listFragment = apodFragmentList.newInstance(3)
        mBottomNavigationBar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.apod -> {
                    setCurrentFragment(apodFragment)
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