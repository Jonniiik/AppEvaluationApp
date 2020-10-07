package com.example.appevaluation


import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


lateinit var button: Button
lateinit var button2: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.main_activity_button_ration_dialog_fragment)

        button2 = findViewById(R.id.main_activity_button_ration_dialog)

        button.setOnClickListener {
            openFragmentDialog()
        }
        button2.visibility = View.VISIBLE
        button2.setOnClickListener {
            supportFragmentManager.inTransaction {
                val ratingFragment = RatingFragment()
                val prev: Fragment? = supportFragmentManager.findFragmentByTag(RatingFragment.RATING_FRAGMENT_TAG)
                if (prev != null) remove(prev)
                replace(R.id.main_activity_fragment_container, ratingFragment)
            }
        }
    }

    /**
     * Find a good way, how i can open Fragment or DialogFragment on Kotlin
     */
    private fun openFragmentDialog() {
        val ratingDialogDialogFragment = RatingDialogDialogFragment()
        val manager = supportFragmentManager
        manager.inTransaction {
            addToBackStack(null)
            val prev: Fragment? = supportFragmentManager.findFragmentByTag(RatingDialogDialogFragment.RATING_DIALOG_TAG)
            if (prev != null) remove(prev)
        }
        ratingDialogDialogFragment.show(manager, RatingDialogDialogFragment.RATING_DIALOG_TAG)
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}