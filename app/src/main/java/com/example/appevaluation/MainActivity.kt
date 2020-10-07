package com.example.appevaluation


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


lateinit var buttonDialogFragment: Button
lateinit var buttonRatingFragment: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonDialogFragment = findViewById(R.id.main_activity_button_ration_dialog_fragment)
        buttonRatingFragment = findViewById(R.id.main_activity_button_ration_dialog)

        buttonDialogFragment.setOnClickListener {
            supportFragmentManager.inTransaction {
                val ratingDialogDialogFragment = RatingDialogDialogFragment()
                addToBackStack(null)
                val prev: Fragment? = supportFragmentManager.findFragmentByTag(RatingDialogDialogFragment.RATING_DIALOG_TAG)
                if (prev != null) remove(prev)
                ratingDialogDialogFragment.show(supportFragmentManager, RatingDialogDialogFragment.RATING_DIALOG_TAG)
            }
        }

        buttonRatingFragment.setOnClickListener {
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
    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}