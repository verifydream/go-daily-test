package com.example.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.example.MainActivity
import com.example.R
import com.example.BrainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class BrainAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action == "com.example.ACTION_UPDATE_WIDGET" || intent.action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context, BrainAppWidgetProvider::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    companion object {
        fun triggerUpdate(context: Context) {
            val intent = Intent(context, BrainAppWidgetProvider::class.java).apply {
                action = "com.example.ACTION_UPDATE_WIDGET"
            }
            context.sendBroadcast(intent)
        }

        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = RemoteViews(context.packageName, R.layout.brain_widget_layout)

            // Intent to open MainActivity when clicking widget
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                Intent(context, MainActivity::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_root, pendingIntent)

            // Fetch data from Room database
            val app = context.applicationContext as? BrainApplication
            if (app != null) {
                app.applicationScope.launch(Dispatchers.IO) {
                    val repository = app.repository
                    
                    // Fetch real transactions and calculate balance
                    val transactions = repository.transactions.firstOrNull() ?: emptyList()
                    var totalIncome = 0.0
                    var totalExpense = 0.0
                    for (t in transactions) {
                        if (t.type == "INCOME") {
                            totalIncome += t.amount
                        } else {
                            totalExpense += t.amount
                        }
                    }
                    val netBalance = totalIncome - totalExpense
                    
                    // Fetch real todos and calculate remaining
                    val todos = repository.todos.firstOrNull() ?: emptyList()
                    val uncompletedCount = todos.count { !it.isCompleted }

                    // Fetch settings to display correct currency
                    val settings = repository.settings.firstOrNull()
                    val currencySymbol = settings?.currency ?: "IDR"

                    launch(Dispatchers.Main) {
                        views.setTextViewText(R.id.widget_balance_value, "$currencySymbol ${netBalance.toInt()}")
                        views.setTextViewText(R.id.widget_tasks_value, "$uncompletedCount Tersisa")
                        
                        appWidgetManager.updateAppWidget(appWidgetId, views)
                    }
                }
            } else {
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}
