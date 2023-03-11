package com.maytemur.firebasefcm.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_HIGH
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_ONE_SHOT
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.maytemur.firebasefcm.R
import com.maytemur.firebasefcm.view.MainActivity
import java.util.*

class FirebaseService : FirebaseMessagingService() {
    val channelId = "/topics/firebase_channel"
    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP) //açık bir aktivite varsa herşeyi kapatıyor
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, FLAG_ONE_SHOT)
        val notificationManager= getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationId= Random().nextInt()


        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            notificationChannelOlustur(notificationManager)
        }
        val notification =
            NotificationCompat.Builder(this, channelId).setContentTitle(p0.data["baslik"])
                .setContentText(p0.data["mesaj"]).setSmallIcon(R.drawable.ic_baseline_call_made_24)
                .setAutoCancel(true).setContentIntent(pendingIntent).build()

        notificationManager.notify(notificationId,notification)
    }
    @RequiresApi(Build.VERSION_CODES.O) //notification channel android O(Oreo-8) dan sonra geldi- başında kontrol ediyoruz
    private fun notificationChannelOlustur (notificationManager: NotificationManager){
        val channelName= "firebaseChannel"
        val channel= NotificationChannel(channelId,channelName,IMPORTANCE_HIGH).apply {
            description= "Kanal Tanımı"
            enableLights(true)
            lightColor= Color.BLUE
        }
        notificationManager.createNotificationChannel(channel)
    }
}