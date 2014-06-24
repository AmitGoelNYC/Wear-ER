package com.example.er;

import android.app.Activity;
import android.app.Fragment;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.BigTextStyle;

public class MainActivity extends Activity {

	Spinner patspinner;
	String input;
	Spinner spinner;
	EditText edittext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}

		patspinner = (Spinner) findViewById(R.id.spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.patient_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		patspinner.setAdapter(adapter);
		
		edittext = (EditText) findViewById(R.id.edittext);
		
		spinner = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				this, R.array.plan_array,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter2);
		
		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	sendMessage();
		    }
		});
		
	}

	protected void sendMessage() {
		String input = edittext.getText().toString();
		if(!input.equals("")){
			int notificationId = 001;
			// Build intent for notification content
			Intent viewIntent = new Intent(this, MainActivity.class);
			viewIntent.putExtra("jj", 2);
			PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0,
					viewIntent, 0);

			// Specify the 'big view' content to display the long
			// event description that may not fit the normal content text.
			BigTextStyle bigStyle = new NotificationCompat.BigTextStyle();
			bigStyle.bigText("yes");

			String patient = patspinner.getSelectedItem().toString();
			
			// Create builder for the main notification
			NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
					this)
					.setSmallIcon(R.drawable.ic_launcher)
					.setContentTitle("Patient")
					.setContentText(
							patient
									+ " in Critical Care needs Urgent injection of .. 30 cc's")
					.setContentIntent(viewPendingIntent)
					.setLargeIcon(
							BitmapFactory.decodeResource(getResources(),
									R.drawable.rsz_darkblu));

			// Create a big text style for the second page
			BigTextStyle secondPageStyle = new NotificationCompat.BigTextStyle();
			secondPageStyle.setBigContentTitle("Details").bigText(
					/*"Vitals  BP - 140/100.  HR - 145.  Currently bleeding internally."
							+ "Requesting immediate assistance"*/
					 input + " " + spinner.getSelectedItem().toString());

			// Create second page notification
			Notification secondPageNotification = new NotificationCompat.Builder(
					this).setStyle(secondPageStyle).build();

			// Create main notification and add the second page
			Notification twoPageNotification = new WearableNotifications.Builder(
					notificationBuilder).addPage(secondPageNotification).build();

			// Get an instance of the NotificationManager service
			NotificationManagerCompat notificationManager = NotificationManagerCompat
					.from(this);

			// Build the notification and issues it with notification manager.
			notificationManager.notify(notificationId, twoPageNotification);
		}
		

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
