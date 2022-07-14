package com.goodslist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goodslist.R
import com.goodslist.view.list.ListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, ListFragment.newInstance())
                .addToBackStack("")
                .commitAllowingStateLoss()
        }
    }
}