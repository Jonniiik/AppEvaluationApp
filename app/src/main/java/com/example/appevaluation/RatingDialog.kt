package com.example.appevaluation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AlertDialog

class RatingDialog(context: Context) : RelativeLayout(context) {
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
            updateViewState()
        }

    /**
     * Сообщение написанное пользователем для разработчиков
     */
    private lateinit var message: String

    /**
     * оценка пользователя
     */
    private var score: Float = 0F

    /**
     * Диалоговое окно
     */
    private lateinit var alertDialog: AlertDialog

    private val inflater = LayoutInflater.from(context)

    private var evaluationSetOnClickListener: OnClickListener? = null
        set(value) {
            field = value

                tvRateScore.visibility = View.VISIBLE

        }


    init {
        inflater.inflate(R.layout.reting_dialog, this)
        llRateBody = findViewById(R.id.rating_dialog_body_rate_linear_layout)
        ratingBar = findViewById(R.id.rating_dialog_body_rate_ratingBar)
        tvRateScore = findViewById(R.id.rating_dialog_body_rate_score_text_vew)
        tvRateScore.visibility = View.GONE
        bEvaluation = findViewById(R.id.rating_dialog_body_rate_evaluation_button)
        tvEvaluationLate =
            findViewById(R.id.rating_dialog_body_rate_evaluation_later_button_text_view)
        llReviewBody = findViewById(R.id.rating_dialog_body_review_linear_layout)
        llReviewBody.visibility = View.GONE
        etReviewTyping = findViewById(R.id.rating_dialog_body_review_typing_text_edit_text)
        bSendEvaluation = findViewById(R.id.rating_dialog_body_review_send_evaluation_button)
        tvCloseReviewWindow = findViewById(R.id.rating_dialog_body_review_close_window_text_view)
        llThanksBody = findViewById(R.id.rating_dialog_thanks_layout_linear_layout)
        llThanksBody.visibility = View.GONE
        tvCloseThanksWindows = findViewById(R.id.rating_dialog_body_thanks_close_text_view)

        ratingBar.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            score = rating
            Log.e("ratingBar", score.toString())
        }
        val test = 0
        bEvaluation.setOnClickListener {
            if (test == 0) {
                tvRateScore.visibility = View.VISIBLE
            } else {
                Toast.makeText(
                    context,
                    "Рейтинг: " + ratingBar.rating.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
            return@setOnClickListener
            //evaluationSetOnClickListener?.onClick(it)
        }


        bSendEvaluation.setOnClickListener {
            var result: Boolean? = null
            if (result!!) {
                status = States.THANKS_BODY
            }
        }
    }

    private fun evaluationOnClick() {
        if (ratingBar.rating == 0F || ratingBar.rating == null) {
            tvRateScore.visibility = View.VISIBLE
        }
        Log.e("evaluationOnClick", ratingBar.rating.toString())
    }

    fun show() {
        val builder = AlertDialog.Builder(context, R.style.AlertDialog)
        builder.setView(inflate(context, R.layout.reting_dialog, this))
        alertDialog = builder.create()
        alertDialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        alertDialog.show()
        Log.e("showRatingBar", "showRatingBar")
    }

    /**
     * Функция для изменения состояни карточки
     */
    private fun updateViewState() {
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
     * Метод для отправки данных по оценке
     */
    private fun sendScore(score: Int, message: String) {}
}
