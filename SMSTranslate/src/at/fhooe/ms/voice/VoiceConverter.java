/**
 * 
 */
package at.fhooe.ms.voice;

import android.content.Context;
import at.fhooe.ms.ftpuploadtest.FTP;

/**
 * This class acts as an interface for:
 * -generating VXML files, 
 * -uploading them to the Voxeo FTP server, and
 * -making/establishing a voice call with the Voxeo server 
 * 
 * @author Xabi
 *
 */
public class VoiceConverter {
	
	private static Context m_context;
	
	public static void HearIt(Context _context, String _inString){
		m_context =_context;
		
		uploadToFTPServer();
		
		makeAudioCall();
	}
	
	private static void uploadToFTPServer(){
		FTP ftp = new FTP(m_context);
		
		ftp.uploadFile();
	}
	
	private static void makeAudioCall(){
		
	}
	
}
