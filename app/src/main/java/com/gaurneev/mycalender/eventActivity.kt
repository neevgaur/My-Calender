package com.gaurneev.mycalender

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.gaurneev.mycalender.databinding.ActivityEventBinding
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class eventActivity : AppCompatActivity() {
    companion object{
        const val d="D"
        const val m="M"
        const val y="Y"
    }

    private var eventBinding: ActivityEventBinding? = null
    private lateinit var picker : MaterialTimePicker
    private lateinit var calender: Calendar
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        eventBinding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(eventBinding?.root)

        val date= intent.getStringExtra(d)
        val month = intent.getStringExtra(m)
        val year = intent.getStringExtra(y)

        createNotificationChannel()

        eventBinding?.btnDate?.setOnClickListener {
            showTimePicker()
        }

        eventBinding?.btnCreate?.setOnClickListener {
            setalarm()
        }


    }

    private fun setalarm() {
        val et = eventBinding?.eventName?.text.toString()
        val nm = eventBinding?.name?.text.toString()

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,alarmReceiver::class.java)
        intent.putExtra(alarmReceiver.title,et)
        intent.putExtra(alarmReceiver.person,nm)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)

        alarmManager.setRepeating(

            AlarmManager.RTC_WAKEUP,calender.timeInMillis,
            AlarmManager.INTERVAL_DAY,pendingIntent
        )

        Toast.makeText(this,"Event set Successfully",Toast.LENGTH_SHORT).show()
        startActivity(Intent(this,MainActivity::class.java))

    }


    private fun showTimePicker() {
        picker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(12)
            .setMinute(0)
            .setTitleText("Select Time to Notify")
            .build()

        picker.show(supportFragmentManager, "calAlarm")

        picker.addOnPositiveButtonClickListener{
            if (picker.hour>12){
                eventBinding?.time?.text =
                    String.format("%02d",picker.hour - 12) + " : "+ String.format("%02d",picker.minute)+ " PM"
            }
            else{
                eventBinding?.time?.text =
                    String.format("%02d",picker.hour) + " : "+ String.format("%02d",picker.minute)+ " AM"

            }

            calender = Calendar.getInstance()
            calender[Calendar.HOUR_OF_DAY]= picker.hour
            calender[Calendar.MINUTE]=picker.minute
            calender[Calendar.SECOND]=0
            calender[Calendar.MILLISECOND]=0
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            val name : CharSequence = "AndroidReminderChannel"
            val description = "Channel For Alarm Manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("calAlarm",name,importance)
            channel.description = description
            val notificationManager = getSystemService(
                NotificationManager::class.java
            )

            notificationManager.createNotificationChannel(channel)
        }
    }
}