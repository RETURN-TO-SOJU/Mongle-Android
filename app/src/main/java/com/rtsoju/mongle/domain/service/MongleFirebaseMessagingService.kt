package com.rtsoju.mongle.domain.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rtsoju.mongle.R
import com.rtsoju.mongle.data.source.local.config.ConfigKey
import com.rtsoju.mongle.domain.usecase.config.GetConfigUseCase
import com.rtsoju.mongle.domain.usecase.user.SetFCMTokenUseCase
import com.rtsoju.mongle.presentation.view.daydetail.DayDetailActivity
import com.rtsoju.mongle.presentation.view.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class MongleFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var setFCMToken: SetFCMTokenUseCase

    @Inject
    lateinit var getConfig: GetConfigUseCase

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            setFCMToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val useAlert = getConfig(ConfigKey.USE_ALERT)
        if (!useAlert) {
            Log.w(TAG, "Alert is disabled. This message ignored.")
            return
        }

        sendNotification(message)
    }

    private fun parseGiftIntent(data: Map<String, String>): Intent {
        val date = data["date"]
        if (date != null) {
            return Intent(this, DayDetailActivity::class.java).apply {
                putExtra(
                    DayDetailActivity.EXTRA_DATE,
                    LocalDate.parse(date)
                )
                putExtra(DayDetailActivity.EXTRA_SHOW_ARRIVED_GIFT_DIALOG, true)
            }
        } else {
            throw IllegalArgumentException("data.date is null")
        }
    }

    private fun parseAnalyzeCompleteIntent(data: Map<String, String>): Intent {
        val date = data["date"]
        if (date != null) {
            return Intent(this, MainActivity::class.java).apply {
                putExtra(MainActivity.EXTRA_ANALYZED_DATE_RANGE, date)
            }
        } else {
            throw IllegalArgumentException("data.date is null")
        }
    }

    private fun parseErrorIntent(): Intent {
        return Intent(this, MainActivity::class.java)
    }

    private fun createIntentFromData(data: Map<String, String>): Intent? {
        val type = data["type"]
        return try {
            when (type) {
                "gift" -> parseGiftIntent(data)
                "analyze" -> parseAnalyzeCompleteIntent(data)
                "error" -> parseErrorIntent()
                else -> throw IllegalArgumentException("Unknown Type: $type")
            }
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, e.message ?: "")
            null
        }
    }

    private fun sendNotification(message: RemoteMessage) {
        val notification = message.notification
        val data = message.data

        if (notification != null && data.isNotEmpty()) {
            val title = notification.title
            val body = notification.body
            val intent = createIntentFromData(data)
            if (title != null && body != null) {
                sendNotification(title, body, intent)
            }
        } else {
            Log.d(TAG, "Receiving Error: Data is wrong.")
        }
    }

    private fun sendNotification(title: String, body: String, resultIntent: Intent?) {
        val uniId = (System.currentTimeMillis() / 7).toInt()
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        Log.d(TAG, " - Title: $title")
        Log.d(TAG, " - Body: $body")

        // 알림에 대한 UI 정보와 작업을 지정한다.
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(
            this,
            CHANNEL_ID
        )
            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
            .setContentTitle(title) // 제목
            .setContentText(body) // 메시지 내용
            .setAutoCancel(true)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setSound(soundUri)

        if (resultIntent != null) {
            val resultPendingIntent: PendingIntent? =
                androidx.core.app.TaskStackBuilder.create(this).run {
                    addNextIntentWithParentStack(resultIntent)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        )
                    } else {
                        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                    }
                }
            notificationBuilder.setContentIntent(resultPendingIntent)
        } else {
            notificationBuilder.setContentText(body)
        }

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요하다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Notice",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())
    }

    companion object {
        private const val TAG = "MongleMongleFirebase"
        private const val CHANNEL_ID = "firebase_messaging_channel"
    }
}