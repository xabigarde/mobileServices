/**
 * 
 */
package at.fhooe.ms.voice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * @author User
 *
 */
public class PhoneCall {

	private static String VOXEO_TELEPHONE = "+43 720 815246";
	
	private Context m_context;

	public PhoneCall(Context _context){
		m_context = _context;
	}
	
	public void makeCall(){
		Intent intent = new Intent(Intent.ACTION_CALL);

		//intent.setData(Uri.parse("tel:" + bundle.getString("mobilePhone")));
		intent.setData(Uri.parse("tel:"+VOXEO_TELEPHONE));
		
		m_context.startActivity(intent);
	}
}
