package com.example.appevaluation

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


/**
 * Dialog window with rating app
 * if we put 1 to 3, run window with review [State.REVIEW_BODY]
 * after [State.REVIEW_BODY] we open [State.THANKS_BODY]
 * if we put 4 to 5, run GooglePlay
 */
class RatingDialogDialogFragment : DialogFragment() {

    companion object {
        @JvmStatic
        val RATING_DIALOG_TAG = "RATING_DIALOG_TAG"
    }

    /**
     * layout with Rating
     */
    private lateinit var llRateBody: LinearLayout //rating_dialog_body_rate_linear_layout
    private lateinit var ratingBar: RatingBar //rating_dialog_body_rate_ratingBar
    private lateinit var tvRateScore: TextView //rating_dialog_body_rate_score_text_vew
    private lateinit var bEvaluation: Button //rating_dialog_body_rate_evaluation_button
    private lateinit var tvEvaluationLate: TextView //rating_dialog_body_rate_evaluation_later_button_text_view

    /**
     * layout with Review
     */
    private lateinit var llReviewBody: LinearLayout //rating_dialog_body_review_linear_layout
    private lateinit var etReviewTyping: EditText //rating_dialog_body_review_typing_text_edit_text
    private lateinit var bSendEvaluation: Button //rating_dialog_body_review_send_evaluation_button
    private lateinit var tvCloseReviewWindow: TextView //rating_dialog_body_review_close_window_text_view

    /**
     * layout thanks
     */
    private lateinit var llThanksBody: LinearLayout //rating_dialog_thanks_layout_linear_layout
    private lateinit var tvCloseThanksWindows: TextView //rating_dialog_body_thanks_close_text_view


    enum class State { RATE_BODY, REVIEW_BODY, THANKS_BODY }

    /**
     * state windows
     */
    private var status = State.RATE_BODY
        set(value) {
            field = value
            when (status) {
                State.RATE_BODY -> {
                    llRateBody.visibility = View.VISIBLE
                    llReviewBody.visibility = View.GONE
                    llThanksBody.visibility = View.GONE
                }
                State.REVIEW_BODY -> {
                    llRateBody.visibility = View.GONE
                    llReviewBody.visibility = View.VISIBLE
                    llThanksBody.visibility = View.GONE
                }
                State.THANKS_BODY -> {
                    llRateBody.visibility = View.GONE
                    llReviewBody.visibility = View.GONE
                    llThanksBody.visibility = View.VISIBLE
                }
            }
        }

    /**
     * review
     */
    private lateinit var message: String

    /**
     * score
     */
    private var score: Int = 0

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = activity?.layoutInflater
        val view: View? = inflater?.inflate(R.layout.reting_dialog, null)

        view?.let {
            llRateBody = it.findViewById(R.id.rating_dialog_body_rate_linear_layout)
            ratingBar = it.findViewById(R.id.rating_dialog_body_rate_ratingBar)
            tvRateScore = it.findViewById(R.id.rating_dialog_body_rate_score_text_vew)
            tvRateScore.visibility = View.GONE
            bEvaluation = it.findViewById(R.id.rating_dialog_body_rate_evaluation_button)
            tvEvaluationLate =
                it.findViewById(R.id.rating_dialog_body_rate_evaluation_later_button_text_view)
            llReviewBody = it.findViewById(R.id.rating_dialog_body_review_linear_layout)
            llReviewBody.visibility = View.GONE
            etReviewTyping = it.findViewById(R.id.rating_dialog_body_review_typing_text_edit_text)
            bSendEvaluation =
                it.findViewById(R.id.rating_dialog_body_review_send_evaluation_button)
            tvCloseReviewWindow =
                it.findViewById(R.id.rating_dialog_body_review_close_window_text_view)
            llThanksBody = it.findViewById(R.id.rating_dialog_thanks_layout_linear_layout)
            llThanksBody.visibility = View.GONE
            tvCloseThanksWindows = it.findViewById(R.id.rating_dialog_body_thanks_close_text_view)
        }

        val builder = AlertDialog.Builder(context!!, R.style.AlertDialog)
        builder.setView(view)

        bEvaluation.setOnClickListener {
            when (ratingBar.rating) {
                0F -> tvRateScore.visibility = View.VISIBLE
                in 1F..3F -> {
                    score = ratingBar.rating.toInt()
                    status = State.REVIEW_BODY
                }
                4F, 5F -> {
                    score = ratingBar.rating.toInt()
                    sendRating(score, null)
                    startIntentToGooglePlay()
                }
            }
        }

        bSendEvaluation.setOnClickListener {
            if (etReviewTyping.text.isNotEmpty()) {
                message = etReviewTyping.toString()

                // метод для отправки рейтинга
                sendRating(score, message)
                status = State.THANKS_BODY
            } else {
                etReviewTyping.let {
                    it.hint =
                        activity?.getString(R.string.rating_dialog_text_hint_body_review_edit_text)
                    activity?.resources?.getColor(R.color.colorTextRed)
                        ?.let { it1 -> it.setHintTextColor(it1) }
                }
            }
        }

        /**
         * Send late
         * void run to send to a server [score] = -1, we will put this dialog again, but late
         */
        tvEvaluationLate.setOnClickListener {
            //Метод для формирования другой даты отзыва
            score = -1
            sendRating(score, null)
        }
        /**
         * close dialog
         */
        tvCloseReviewWindow.setOnClickListener { dialog?.cancel() }
        tvCloseThanksWindows.setOnClickListener { dialog?.cancel() }


        return builder.create()
    }

    /**
     * void send rating to server
     */
    private fun sendRating(score: Int, @Nullable message: String?) {
        try {
            //Метод отправки
            //sendRatingVote(ls, score, message, apiCallbackSendRating)
        } catch (e: Exception) {
            e.printStackTrace()

            if (score == 4 or 5)
                startIntentToGooglePlay()

            // Запускаем сервис что бы в фоне отправить данные, если данные не ушли
            startRatingService(score, message)
        }
    }
    /**
    private var apiCallbackSendRating = Callback { result: Boolean, data: AddRatingVoteAnswerData? ->
    when (score) {
    -1 -> {
    dialog?.cancel()
    if (!result)
    startRatingService(score, null)
    }
    in 1..3 -> {
    status = State.THANKS_BODY
    if (!result)
    startRatingService(score, etReview.text.toString())
    }
    4, 5 -> {
    startIntentToGooglePlay()
    if (!result)
    startRatingService(score, null)
    }
    }
    }
     */

    /**
     * run to GooglePlay
     */
    private fun startIntentToGooglePlay() {
        val url = Uri.parse("https://play.google.com/store/apps/details?id=ru.tns.tnsmobile")
        val openLinkIntent = Intent(Intent.ACTION_VIEW, url)
        if (openLinkIntent.resolveActivity((activity as MainActivity).packageManager) != null) {
            startActivity(openLinkIntent)
        }
        dialog?.cancel()
    }
    /**
     * if we cannot send the rating, we run service
     */
    private fun startRatingService(score: Int, @Nullable message: String?) {
        val ratingService = RatingService()
        val intent = ratingService.newIntent(activity as MainActivity, score, message.toString())
        (activity as MainActivity).startService(intent)
    }
}