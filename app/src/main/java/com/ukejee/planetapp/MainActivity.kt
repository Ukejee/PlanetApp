package com.ukejee.planetapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ukejee.planetapp.ui.planet.PlanetListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        savedInstanceState ?: addFragment()
    }

    private fun addFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, PlanetListFragment())
        transaction.commit()
    }
}
