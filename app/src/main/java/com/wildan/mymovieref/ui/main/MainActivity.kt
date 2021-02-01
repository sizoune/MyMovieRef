package com.wildan.mymovieref.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wildan.mymovieref.R
import com.wildan.mymovieref.databinding.ActivityMainBinding
import com.wildan.mymovieref.ui.main.adapter.SectionsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupUI()
    }

    private fun setupUI() {
        with(binding.toolbar) {
            inflateMenu(R.menu.toolbar_favorite)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.myFav) {
                    val uri = Uri.parse("mymovieref://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                }
                true
            }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        binding.viewPager.adapter = sectionsPagerAdapter
        binding.tabs.setupWithViewPager(binding.viewPager)
    }

}