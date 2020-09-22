package com.example.appevaluation


import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction


lateinit var button1: Button
lateinit var button2: Button
lateinit var rl: RelativeLayout


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1 = findViewById(R.id.main_activity_button_ration_dialog)
        button2 = findViewById(R.id.main_activity_button_ration_dialog_fragment)
        rl = findViewById(R.id.main_activity_fragment)

        button1.setOnClickListener {
            RatingDialog(this).show()
        }
        button2.setOnClickListener {
            val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment: Fragment = RatingDialogDialogFragment().newInstance()!!
            ft.add(R.id.main_activity_fragment, fragment)
            ft.commit()

        }
    }
}