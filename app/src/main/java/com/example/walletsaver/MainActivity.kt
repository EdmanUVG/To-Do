package com.example.walletsaver


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getWindow().setNavigationBarColor(getResources().getColor(R.color.navigationBarColor));

        setSupportActionBar(toolbar)

        NavigationUI.setupActionBarWithNavController(this, findNavController(R.id.nav_host_fragment))

    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment).navigateUp()
}
