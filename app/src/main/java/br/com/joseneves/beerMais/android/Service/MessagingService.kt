package br.com.joseneves.beerMais.android.Service

import android.app.NotificationChannel
import com.google.firebase.messaging.FirebaseMessagingService
import android.app.NotificationManager
import android.app.PendingIntent
import br.com.joseneves.beerMais.android.R
import br.com.joseneves.beerMais.android.Main.MainActivity
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.RemoteMessage
import android.graphics.BitmapFactory
import kotlin.random.Random


class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        var title = remoteMessage?.notification?.title
        if (title.isNullOrEmpty()) {
            title = applicationContext.getString(R.string.app_name)
        }

        var message = remoteMessage?.notification?.body
        if (message.isNullOrEmpty()) {
            message = applicationContext.getString(R.string.defaultNotification)
        }

        showNotification(title!!, message!!)
    }

    private fun showNotification(title: String, message: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "YOUR_CHANNEL_ID",
                "YOUR_CHANNEL_NAME",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = "YOUR_NOTIFICATION_CHANNEL_DESCRIPTION"
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, "YOUR_CHANNEL_ID")
            .setSmallIcon(R.drawable.cheers_icon)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.applicationContext.resources,
                    R.drawable.cheers_icon
                )
            )
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
        val intent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        notificationBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(Random.nextInt(60000), notificationBuilder.build())
    }

}