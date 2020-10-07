package com.example.appevaluation


import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

lateinit var button: Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.main_activity_button_ration_dialog_fragment)

        button.setOnClickListener {
            openFragmentDialog()
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