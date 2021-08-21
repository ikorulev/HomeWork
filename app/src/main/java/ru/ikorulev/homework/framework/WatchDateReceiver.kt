package ru.ikorulev.homework.framework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import ru.ikorulev.homework.R
import ru.ikorulev.homework.presentation.view.MainActivity

class WatchDateReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val appName = context.getString(R.string.app_name)
        val filmTitle = intent.getStringExtra("filmTitle")
        val filmPath = intent.getStringExtra("filmPath")
        val bitmap = runBlocking(Dispatchers.IO) {
            val futureTarget = Glide.with(context)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w342${filmPath}")
                .submit()
            return@runBlocking futureTarget.get()
        }
        var filmId = 0
        if (intent.action != null) filmId = intent.action!!.toInt()
        val filmIntent = Intent(context, MainActivity::class.java)
        filmIntent.action = intent.action
        filmIntent.putExtra("filmTitle", filmTitle)
        filmIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent =
            PendingIntent.getActivity(context, 0, filmIntent, PendingIntent.FLAG_ONE_SHOT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(appName, appName, importance)
            channel.description = appName
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            if (notificationManager.getNotificationChannel(appName) == null) {
                notificationManager.createNotificationChannel(channel)
            }
        }

        val builder = NotificationCompat.Builder(context, context.getString(R.string.app_name))
            .setSmallIcon(R.drawable.detail)
            .setContentTitle(appName)
            .setContentText(filmTitle)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT) // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.detail, "Details", pendingIntent)
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setAutoCancel(true)
        val notificationManager = NotificationManagerCompat.from(context)
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(filmId, builder.build())
    }


}