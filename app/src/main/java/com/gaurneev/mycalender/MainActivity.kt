package com.gaurneev.mycalender

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.gaurneev.mycalender.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var mainBinding:ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding?.root)

        mainBinding?.calendarView?.setOnDateChangeListener { calendarView, i, i2, i3 ->
            val y = (i2+1).toString()
            val z = i.toString()
            val x = i3.toString()

            mainBinding?.btnCreate?.visibility = View.VISIBLE

            Toast.makeText(this, "Selected Date: $x/$y/$z",Toast.LENGTH_LONG).show()
            mainBinding?.btnCreate?.setOnClickListener {
                val intent = Intent(this,eventActivity::class.java)
                intent.putExtra(eventActivity.d,x)
                intent.putExtra(eventActivity.m,y)
                intent.putExtra(eventActivity.y,z)
                startActivity(intent)
            }
        }
    }
}