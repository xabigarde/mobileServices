package at.fhooe.ms.gui;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import at.fhooe.ms.smstranslate.R;
import at.fhooe.ms.translator.Languages;
import at.fhooe.ms.translator.Translator;
import at.fhooe.ms.voice.VoiceConverter;

public class SMSReceiveActivity extends Activity implements OnClickListener,
		Observer {

	private Button btnTranslate;
	private Button btnHearIt;
	private TextView tvSmsText;

	private Translator translator;

	private String lastReceivedMessage;
	private String lastTranslatedMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_smsreceive);

		btnTranslate = (Button) findViewById(R.id.btnTranslate);
		btnTranslate.setOnClickListener(this);

		btnHearIt = (Button) findViewById(R.id.btnHearIt);
		btnHearIt.setOnClickListener(this);

		tvSmsText = (TextView) findViewById(R.id.txtReceivedSMS);

		Log.i("smsreceive", "onCreate");

		translator = new Translator(this);
		translator.addObserver(this);

		onSmsReceive(getIntent());
	}

	@Override
	protected void onNewIntent(Intent i) {
		super.onNewIntent(i);

		Log.i("smsreceive", "onNewIntent");

		onSmsReceive(i);
	}

	/**
	 * @param i
	 */
	private void onSmsReceive(Intent intent) {
		// Retrieves a map of extended data from the intent.
		final Bundle bundle = intent.getExtras().getBundle("smsbundle");

		try {

			if (bundle != null) {

				final Object[] pdusObj = (Object[]) bundle.get("pdus");

				for (int i = 0; i < pdusObj.length; i++) {

					SmsMessage currentMessage = SmsMessage
							.createFromPdu((byte[]) pdusObj[i]);
					String phoneNumber = currentMessage
							.getDisplayOriginatingAddress();

					String senderNum = phoneNumber;
					lastReceivedMessage = currentMessage
							.getDisplayMessageBody();

					Log.i("SmsReceiver", "senderNum: " + senderNum
							+ "; message: " + lastReceivedMessage);

					// Show Alert
					// int duration = Toast.LENGTH_LONG;
					// Toast toast = Toast.makeText(this, "senderNum: "
					// + senderNum + ", message: " + lastReceivedMessage,
					// duration);
					// toast.show();

				} // end for loop
			} // bundle is null

		} catch (Exception e) {
			Log.e("SmsReceiver", "Exception smsReceiver" + e);

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.smsreceive, menu);
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
		case R.id.btnTranslate:

			showTranslationWaitScreen();

			SharedPreferences pref = PreferenceManager
					.getDefaultSharedPreferences(this);

			String language = pref.getString("language_list", "0");
			int languageIndex = Integer.parseInt(language);

			String chosenLang = Languages.ENGLISH;

			switch (languageIndex) {
			case 0:
				chosenLang = Languages.ENGLISH;
				break;
			case 1:
				chosenLang = Languages.GERMAN;
				break;
			case 2:
				chosenLang = Languages.FRENCH;
				break;
			case 3:
				chosenLang = Languages.SPANISH;
				break;
			case 4:
				chosenLang = Languages.RUSSIAN;
				break;
			}

			translator.translate(lastReceivedMessage, Languages.AUTOMATIC,
					chosenLang);
			break;
		case R.id.btnHearIt:
			
			// TODO: call the generateXML + call initiation
			//VoiceConverter.HearIt(this, lastTranslatedMessage);
			
			String _inString = "Hello, this is a test case that should be working."; 
			VoiceConverter.HearIt(this, _inString);
			
			break;
		}

	}

	/**
	 * 
	 */
	private void showTranslationWaitScreen() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable arg0, Object data) {
		Log.i("smsreceive", "update of observer: " + data.toString());

		tvSmsText.setText(data.toString());

	}
}
