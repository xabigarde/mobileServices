/**
 * 
 */
package at.fhooe.ms.smsreceivetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import at.fhooe.ms.gui.SMSReceiveActivity;

public class IncomingSms extends BroadcastReceiver {

	// Get the object of SmsManager
	final SmsManager sms = SmsManager.getDefault();

	public void onReceive(Context context, Intent intent) {

		Intent newIntent = new Intent(context, SMSReceiveActivity.class);
		newIntent.putExtra("smsbundle", intent.getExtras());
		newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		newIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		context.startActivity(newIntent);
	}
}