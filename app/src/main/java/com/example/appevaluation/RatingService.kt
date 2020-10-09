package com.example.appevaluation

import android.app.AlarmManager
import android.app.IntentService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Handler
import android.os.SystemClock
import java.util.concurrent.TimeUnit


class RatingService : IntentService("RatingService") {

    private var mHandler: Handler = Handler()

    //private var api: API? = null

    private var score: Int = 0
    private var message: String? = null

    /**
     * every 3 hours we try to send rating
     */
    private var intervalSendTime: Long = TimeUnit.MINUTES.toHours(3)

    companion object {

        private const val SCORE_PATH = "SCORE_PATH"
        private const val MESSAGE_PATH = "MESSAGE_PATH"

        fun newIntent(context: Context, score: Int, message: String?): Intent? {
            return Intent(context, RatingService::class.java)
                .putExtra(SCORE_PATH, score)
                .putExtra(MESSAGE_PATH, message)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.extras?.apply {
            score = getInt(SCORE_PATH)
            message = getString(MESSAGE_PATH)
        }

        try {
            //api?.sendRatingVote(api?.ls, score, message, apiCallbackSendRating)
            mHandler.post(DisplayToast(this, "Отправка Оценки на сервер началась"))
        } catch (e: Exception) {
            mHandler.post(DisplayToast(this, "Данные не отправились"))
            //Метод если не отправилиь данные сразу
            serviceAlarm(this, true)
        }
    }
    /**
    private var apiCallbackSendRating = Callback { result: Boolean, data: AddRatingVoteAnswerData? ->
    if (result) {
    mHandler.post(DisplayToast(this, "Отправка Оценки на сервер Выполнена"))
    //Если result = true то мы останавливаем повторную отпраку
    serviceAlarm(this, false)
    } else {
    //Метод если не отправилиь данные сразу
    serviceAlarm(this, true)
    }
    }
     */

    /**
     * If we couldn't send rating
     * We run the Alarm
     * it will check every 3 hours online phone, if yes we will try send a data again
     */
    private fun serviceAlarm(context: Context, isOn: Boolean) {
        if (isOnline()) {
            val intent: Intent? = newIntent(context, score, message)
            val pendingIntent = PendingIntent.getService(context, 0, intent!!, 0)
            val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
            if (isOn) {
                alarmManager.setRepeating(
                    AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), intervalSendTime, pendingIntent
                )
            } else {
                alarmManager.cancel(pendingIntent)
                pendingIntent?.cancel()
            }
        }
    }

    private fun isOnline(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.isActiveNetworkMetered
    }
}
