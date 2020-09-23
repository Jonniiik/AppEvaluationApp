package com.example.appevaluation

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


/**
 * Диалоговое окно с рейтингом приложения
 * Если пользователь поставил от 1 до 3 то мы выводим окно с отзывом [States.REVIEW_BODY]
 * Усли пользователь стаит 4 или 5 отправляем его оценку [States.THANKS_BODY]
 */
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


    enum class States { RATE_BODY, REVIEW_BODY, THANKS_BODY }

    /**
     * Состояние Окна
     */
    private var status = States.RATE_BODY
        set(value) {
            field = value
            when (status) {
                States.RATE_BODY -> {
                    llRateBody.visibility = View.VISIBLE
                    llReviewBody.visibility = View.GONE
                    llThanksBody.visibility = View.GONE
                }
                States.REVIEW_BODY -> {
                    llRateBody.visibility = View.GONE
                    llReviewBody.visibility = View.VISIBLE
                    llThanksBody.visibility = View.GONE
                }
                States.THANKS_BODY -> {
                    llRateBody.visibility = View.GONE
                    llReviewBody.visibility = View.GONE
                    llThanksBody.visibility = View.VISIBLE
                }
            }
        }

    /**
     * Сообщение написанное пользователем для разработчиков
     */
    private lateinit var message: String

    /**
     * оценка пользователя
     */
    private var score: Float = 0F

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = context?.let { AlertDialog.Builder(it, R.style.AlertDialog) }
        val inflater = activity!!.layoutInflater
        val view: View = inflater.inflate(R.layout.reting_dialog, null)
        builder?.setView(view)

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
            when (ratingBar.rating) {
                0F -> tvRateScore.visibility = View.VISIBLE
                in 1F..3F -> {
                    score = ratingBar.rating
                    status = States.REVIEW_BODY
                }
                else -> {
                    // метод для отправки рейтинга

                    startActivity(Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=ru.tns.tnsmobile")))
                }
            }
        }

        bSendEvaluation.setOnClickListener {
            if (etReviewTyping.text.isNotEmpty()) {
                message = etReviewTyping.toString()

                // метод для отправки рейтинга

                status = States.THANKS_BODY
            } else {
                etReviewTyping.let {
                    it.hint =
                        activity?.getString(R.string.rating_dialog_text_hint_body_review_edit_text)
                        activity?.resources?.getColor(R.color.colorTextRed)
                        ?.let { it1 -> it.setHintTextColor(it1) }
                }
            }
        }


        tvEvaluationLate.setOnClickListener {
            //Метод для формирования другой даты отзыва
        }

        tvCloseReviewWindow.setOnClickListener { dialog?.cancel() }
        tvCloseThanksWindows.setOnClickListener { dialog?.cancel() }


        if (builder != null)
            return builder.create()
        return super.onCreateDialog(savedInstanceState)
    }
}