package com.won983212.mongle.domain.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.won983212.mongle.R
import com.won983212.mongle.data.remote.api.RequestErrorType
import com.won983212.mongle.data.remote.api.RequestLifecycleCallback
import com.won983212.mongle.domain.repository.UserRepository
import com.won983212.mongle.presentation.view.daydetail.DayDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MongleFirebaseMessagingService : FirebaseMessagingService(), RequestLifecycleCallback {

    @Inject
    lateinit var userRepository: UserRepository

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.setFCMToken(this@MongleFirebaseMessagingService, token)
        }
    }

    override fun onCreate() {
        Log.d(TAG, "Retrieve FCM Token...")
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            Log.d(TAG, task.result)
        })
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notification: RemoteMessage.Notification? = message.notification
        val data: Map<String, String> = message.data

        if (notification != null) {
            sendNotification(notification.title!!, notification.body!!)
        } else if (data.isNotEmpty()) {
            sendNotification(data["title"]!!, data["body"]!!)
        } else {
            Log.d(TAG, "Receiving Error: Data is empty.")
        }
    }

    private fun sendNotification(title: String, body: String) {
        val uniId = (System.currentTimeMillis() / 7).toInt()
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        Log.d(TAG, " - Title: $title")
        Log.d(TAG, " - Body: $body")

        val resultIntent = Intent(this, DayDetailActivity::class.java).apply {
            // TODO Intent extra for today data
            //putExtra("Hello", "Nice")
        }
        val resultPendingIntent: PendingIntent? = androidx.core.app.TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            } else {
                getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }

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
            .setContentIntent(resultPendingIntent)
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

    override fun onStart() {
    }

    override fun onComplete() {
    }

    override fun onError(requestErrorType: RequestErrorType, msg: String) {
    }

    companion object {
        private const val TAG = "FirebaseService"
        private const val CHANNEL_ID = "firebase_messaging_channel"
    }
}