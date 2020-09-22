package com.example.appevaluation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment

class RatingDialogDialogFragment : DialogFragment() {
    /**
     * Слой для тела оценки
     */
    private lateinit var llRateBody: LinearLayout //rating_dialog_body_rate_linear_layout
    private lateinit var ratingBar: RatingBar //rating_dialog_body_rate_ratingBar
    private lateinit var tvRateScore: TextView //rating_dialog_body_rate_score_text_vew
    private lateinit var bEvaluation: Button //rating_dialog_body_rate_evaluation_button
    private lateinit var tvEvaluationLate: TextView //rating_dialog_body_rate_evaluation_later_button_text_view

    /**
     * Слой для тела ввода отзыва
     */
    private lateinit var llReviewBody: LinearLayout //rating_dialog_body_review_linear_layout
    private lateinit var etReviewTyping: EditText //rating_dialog_body_review_typing_text_edit_text
    private lateinit var bSendEvaluation: Button //rating_dialog_body_review_send_evaluation_button
    private lateinit var tvCloseReviewWindow: TextView //rating_dialog_body_review_close_window_text_view

    /**
     * Слой Спасибо
     */
    private lateinit var llThanksBody: LinearLayout //rating_dialog_thanks_layout_linear_layout
    private lateinit var tvCloseThanksWindows: TextView //rating_dialog_body_thanks_close_text_view

    fun newInstance(): RatingDialogDialogFragment? {
        return RatingDialogDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reting_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llRateBody = view.findViewById(R.id.rating_dialog_body_rate_linear_layout)
        ratingBar = view.findViewById(R.id.rating_dialog_body_rate_ratingBar)
        tvRateScore = view.findViewById(R.id.rating_dialog_body_rate_score_text_vew)
        tvRateScore.visibility = View.GONE
        bEvaluation = view.findViewById(R.id.rating_dialog_body_rate_evaluation_button)
        tvEvaluationLate =
            view.findViewById(R.id.rating_dialog_body_rate_evaluation_later_button_text_view)
        llReviewBody = view.findViewById(R.id.rating_dialog_body_review_linear_layout)
        llReviewBody.visibility = View.GONE
        etReviewTyping = view.findViewById(R.id.rating_dialog_body_review_typing_text_edit_text)
        bSendEvaluation = view.findViewById(R.id.rating_dialog_body_review_send_evaluation_button)
        tvCloseReviewWindow =
            view.findViewById(R.id.rating_dialog_body_review_close_window_text_view)
        llThanksBody = view.findViewById(R.id.rating_dialog_thanks_layout_linear_layout)
        llThanksBody.visibility = View.GONE
        tvCloseThanksWindows = view.findViewById(R.id.rating_dialog_body_thanks_close_text_view)



        bEvaluation.setOnClickListener {
            Toast.makeText(context, "score", Toast.LENGTH_SHORT).show()
        }
    }

}