package tk.giesecke.myhomecontrol;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class SecurityWidget extends AppWidgetProvider {

	/**
	 * Updates a widgets
	 *
	 * @param context
	 *            Context of this application
	 * @param appWidgetManager
	 *            Instance of the appWidgetManager
	 * @param appWidgetId
	 *            ID of the widget to be updated
	 * @param alarmIsActive
	 *            Flag if alarm is on or off
	 */
	static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
								int appWidgetId, boolean alarmIsActive) {

		// Construct the RemoteViews object
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.security_widget);

		if (alarmIsActive) {
			views.setImageViewResource(R.id.iv_sec_widget, R.mipmap.ic_alarm_on);
		} else {
			views.setImageViewResource(R.id.iv_sec_widget, R.mipmap.ic_alarm_autooff);
		}

		// Create an intent to launch the service on widget push
		Intent serviceIntent = new Intent(context, SecurityWidgetClick.class);
		serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
		serviceIntent.putExtra("AlarmStatus", alarmIsActive);
		// PendingIntent is required for the onClickPendingIntent that actually
		// starts the service from a button click
		PendingIntent pendingServiceIntent =
				PendingIntent.getService(context, 0, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

		// Get the layout for the App Widget and attach a click listener to the
		// button
		views.setOnClickPendingIntent(R.id.sec_widget, pendingServiceIntent);

		// Instruct the widget manager to update the widget
		appWidgetManager.updateAppWidget(appWidgetId, views);
	}
}

