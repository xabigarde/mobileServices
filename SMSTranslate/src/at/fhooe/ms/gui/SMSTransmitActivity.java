package at.fhooe.ms.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import at.fhooe.ms.smstranslate.R;

public class SMSTransmitActivity extends Activity implements OnClickListener {

	private Button btnTransmit;
	private EditText etRecipient;
	private EditText etMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smstransmit);

		btnTransmit = (Button) findViewById(R.id.btnTransmit);
		btnTransmit.setOnClickListener(this);

		etRecipient = (EditText) findViewById(R.id.etRecipientNumber);
		etMessage = (EditText) findViewById(R.id.etSMSText);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.smstransmit, menu);
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
		case R.id.btnTransmit:

			String recipient = etRecipient.getText().toString();
			String message = etMessage.getText().toString();

			sendSMS(recipient, message);

			break;
		}

	}

	private void sendSMS(String phoneNumber, String message) {
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNumber, null, message, null, null);
	}

}
