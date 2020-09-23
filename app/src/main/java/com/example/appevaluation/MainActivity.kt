package com.example.appevaluation


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

lateinit var button: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.main_activity_button_ration_dialog_fragment)

        button.setOnClickListener {
            val dialog = RatingDialogDialogFragment()
            val manager = supportFragmentManager
            dialog.show(manager, "dialog")
        }
    }
}