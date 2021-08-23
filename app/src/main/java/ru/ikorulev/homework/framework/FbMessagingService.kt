package ru.ikorulev.homework.framework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.ikorulev.homework.R
import ru.ikorulev.homework.presentation.view.MainActivity

class FbMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FbMessagingService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            sendNotification(remoteMessage.data.toString())
        }
    }

    private fun sendNotification(messageBody: String) {
        val appName = getString(R.string.app_name)
        val filmIntent = Intent(this, MainActivity::class.java)
        filmIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        filmIntent.putExtra("filmTitle", messageBody)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, filmIntent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val builder = NotificationCompat.Builder(this, appName)
            .setSmallIcon(R.drawable.detail)
            .setContentTitle("Cloud Message $appName")
            .setContentText(messageBody)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(appName, appName, importance)
            channel.description = appName
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            if (notificationManager.getNotificationChannel(appName) == null) {
                notificationManager.createNotificationChannel(channel)
            }
        }

        notificationManager.notify(0 /* ID of notification */, builder.build())
    }
}