package com.mutsanna.githubuseruiuxdanapi.alarammanager

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.mutsanna.githubuseruiuxdanapi.MainActivity
import com.mutsanna.githubuseruiuxdanapi.R
import java.util.*

class AlarmReceiver: BroadcastReceiver() {
    companion object{
        private const val ID_REPEATING = 100
        private const val TIME_FORMAT = "HH:mm"
        private const val CHANNEL_NAME = "channel_name"
        private const val CHANNEL_ID = "channel_id"
        private const val NOTIF_ID = 1
    }

    fun setRepeatAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY, 9)
        calender.set(Calendar.MINUTE, 0)
        calender.set(Calendar.SECOND, 0)

        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, "Alarm Set Successfully", Toast.LENGTH_SHORT).show()
    }

    fun cancelAlarm(context: Context?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
        stopNotif(context)

        Toast.makeText(context, "Abort The Alarm", Toast.LENGTH_SHORT).show()
    }

    private fun showNotif(context: Context) {
        val notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_notifications_24)
            .setContentTitle("Reminder")
            .setContentText("Let's check the github application")
            .setVibrate(longArrayOf(1000, 1000, 1000))
            .setSound(alarmSound)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000)

            notifManager.createNotificationChannel(channel)
            builder.setChannelId(CHANNEL_ID)
        }

        val notif = builder.build()
        notifManager.notify(NOTIF_ID, notif)
    }

    private fun stopNotif(context: Context) {
        val notifManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifManager.cancel(NOTIF_ID)
    }

    override fun onReceive(context: Context, intent: Intent?) {
        showNotif(context)
    }
}