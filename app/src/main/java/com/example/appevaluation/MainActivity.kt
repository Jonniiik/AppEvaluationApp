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
        buttonDialogFragment = findViewById(R.id.main_activity_button_rating_dialog_fragment)
        buttonRatingFragment = findViewById(R.id.main_activity_button_rating_fragment)

        buttonDialogFragment.setOnClickListener {
            supportFragmentManager.inTransaction {
                val ratingDialogFragment = RatingDialogFragment()
                removeFragment(RatingDialogFragment.RATING_DIALOG_TAG)
                ratingDialogFragment.show(supportFragmentManager, RatingDialogFragment.RATING_DIALOG_TAG)
            }
        }

        buttonRatingFragment.setOnClickListener {
            supportFragmentManager.inTransaction {
                val ratingFragment = RatingFragment()
                removeFragment(RatingFragment.RATING_FRAGMENT_TAG)
                replace(R.id.main_activity_fragment_container, ratingFragment)
            }
        }
    }

    /**
     * Find fragment, if we have open fragment, we will close it
     */
    private fun AppCompatActivity.removeFragment(tag: String?){
        supportFragmentManager.inTransaction {
            val prev: Fragment? = supportFragmentManager.findFragmentByTag(tag)
            if (prev != null) remove(prev)
        }
    }

    /**
     * Find a good way, how i can open Fragment or DialogFragment on Kotlin
     * Use for open fragments and fragmentDialogs
     */
    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}