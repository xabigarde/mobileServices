package at.fhooe.ms.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import at.fhooe.ms.smstranslate.R;

public class MainActivity extends Activity implements OnClickListener {

	private Button btnWriteSMS;
	private Button btnReadSMS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnWriteSMS = (Button) findViewById(R.id.btnWriteSMS);
		btnWriteSMS.setOnClickListener(this);

		btnReadSMS = (Button) findViewById(R.id.btnReadSMS);
		btnReadSMS.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.action_settings:
			Intent i = new Intent(this, SettingsActivity.class);
			startActivity(i);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnReadSMS:
			startActivity(new Intent(this, SMSReceiveActivity.class));
			break;
		case R.id.btnWriteSMS:
			startActivity(new Intent(this, SMSTransmitActivity.class));
			break;
		}

	}
}
