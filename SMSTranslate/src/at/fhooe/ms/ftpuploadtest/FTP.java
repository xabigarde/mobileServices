/**
 * 
 */
package at.fhooe.ms.ftpuploadtest;

import org.apache.commons.net.ftp.FTPClient;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import at.fhooe.ms.smstranslate.R;

public class FTP extends Activity implements OnClickListener {
	
	private static final String TAG = "FTP_ACTIVITY";

	/**					remote FTP Server name		 */
	static final String FTP_HOST_NAME = "ftp.voxeo.net";
	
	/********* work only for Dedicated IP ***********/
	static final String FTP_HOST_ADDRESS = "66.193.54.42";

	/********* FTP USERNAME ***********/
	static final String FTP_USER = "mobserv";

	/********* FTP PASSWORD ***********/
	static final String FTP_PASS = "rabbit.flesh27";
	
	static final String FTP_WORKING_DIRECTORY = "root/www";

	Button btn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ftp);

		btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(this);

	}

	public void onClick(View v) {

		
		try {
			//System.out.println(">>Trying to create test.xml");
			//File f = new File("test.xml");
			//System.out.println("created?: " + f.createNewFile());
			
			System.out.println(">>Callin async task");
			AsyncTask<URL, Integer, Boolean> asyncTask = new FTPUploadTask();
			asyncTask.execute();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// FTPUploadTask uploadTask = new FTPUploadTask();
		// uploadTask.execute();

	}
	
	private void uploadFile(InputStream _is){
		FTPClient ftpClient = new FTPClient();

		System.out.println(">>Trying to establish connection...");
		try {
		    ftpClient.connect(InetAddress.getByName(FTP_HOST_ADDRESS));
		    boolean connected = ftpClient.login(FTP_USER, FTP_PASS);
		    
		    if(connected)
		    	System.out.println(">>Connected!");
		    
		    ftpClient.changeWorkingDirectory(FTP_WORKING_DIRECTORY);

		    if (ftpClient.getReplyString().contains("250") || connected) {
		        ftpClient.setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
		        BufferedInputStream buffIn = null;
		        
		        //buffIn = new BufferedInputStream(new FileInputStream(FULL_PATH_TO_LOCAL_FILE));
		        buffIn = new BufferedInputStream(_is);
		        ftpClient.enterLocalPassiveMode();
		        //ProgressInputStream progressInput = new ProgressInputStream(buffIn, progressHandler);

		        //boolean result = ftpClient.storeFile(localAsset.getFileName(), progressInput);
		        System.out.println("Uploading kk file");
		        boolean result = ftpClient.storeFile("kk.txt", _is);
		        System.out.println(">>Success storing file? "+result);
		        buffIn.close();
		        ftpClient.logout();
		        ftpClient.disconnect();
		    } else {
		    	System.out.println(">>Connection seems to have failed");
		    }

		} catch (SocketException e) {
		    Log.e(TAG, e.getStackTrace().toString());
		} catch (UnknownHostException e) {
		    Log.e(TAG, e.getStackTrace().toString());
		} catch (IOException e) {
		    Log.e(TAG, e.getStackTrace().toString());
		}
	}

	/*
	public void uploadFile(InputStream is) {

		FTPClient client = new FTPClient();

		try {

			client.connect(FTP_HOST, 21);
			client.login(FTP_USER, FTP_PASS);
			client.setType(FTPClient.TYPE_BINARY);
			client.changeDirectory("/www/");

			// client.upload(fileName, new MyTransferListener());
			client.upload("test", is, 0, 0, new MyTransferListener());

		} catch (Exception e) {
			e.printStackTrace();
			try {
				client.disconnect(true);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	/******* Used to file upload and show progress **********/
/*
	public class MyTransferListener implements FTPDataTransferListener {

		public void started() {

			btn.setVisibility(View.GONE);
			// Transfer started
			Toast.makeText(getBaseContext(), " Upload Started ...",
					Toast.LENGTH_SHORT).show();
			// System.out.println(" Upload Started ...");
		}

		public void transferred(int length) {

			// Yet other length bytes has been transferred since the last time
			// this
			// method was called
			Toast.makeText(getBaseContext(), " transferred ..." + length,
					Toast.LENGTH_SHORT).show();
			// System.out.println(" transferred ..." + length);
		}

		public void completed() {

			btn.setVisibility(View.VISIBLE);
			// Transfer completed

			Toast.makeText(getBaseContext(), " completed ...",
					Toast.LENGTH_SHORT).show();
			// System.out.println(" completed ..." );
		}

		public void aborted() {

			btn.setVisibility(View.VISIBLE);
			// Transfer aborted
			Toast.makeText(getBaseContext(),
					" transfer aborted , please try again...",
					Toast.LENGTH_SHORT).show();
			// System.out.println(" aborted ..." );
		}

		public void failed() {

			btn.setVisibility(View.VISIBLE);
			// Transfer failed
			System.out.println(" failed ...");
		}

	}
*/
	private class FTPUploadTask extends AsyncTask<URL, Integer, Boolean> {
		// Do the long-running work in here
		protected Boolean doInBackground(URL... urls) {
			System.out.println(">>test doInBackground");

			// final File f = new File("/vxml/reco.xml");

			InputStream is = getResources().openRawResource(R.raw.reco); //upload dummy XML file

			uploadFile(is);

			return false;
		}

		// This is called each time you call publishProgress()
		protected void onProgressUpdate(Integer... progress) {

		}

		// This is called when doInBackground() is finished
		protected void onPostExecute(Long result) {

		}
	}

}