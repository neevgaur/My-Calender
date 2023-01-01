package com.gaurneev.mycalender

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class alarmReceiver :BroadcastReceiver() {
    companion object{
        const val title = "TITLE"
        const val person = "PERSON"
    }
    override fun onReceive(context: Context?, intent: Intent?) {

        val e = intent?.getStringExtra(title)
        val n = intent?.getStringExtra(person)

        val i = Intent(context,MainActivity::class.java)

        intent!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0,i,0)

        val builder = NotificationCompat.Builder(context!!,"calAlarm")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Today is $n's $e.")
            .setContentText("My Calender")
            .setAutoCancel(true)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(123,builder.build( ))
    }
}