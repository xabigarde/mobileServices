/**
 * 
 */
package at.fhooe.ms.voice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import org.xmlpull.v1.XmlSerializer;

import android.content.Context;
import android.os.Environment;
import android.util.Xml;

/**
 * @author User
 *
 */
public class VXMLGenerator {

	private Context m_context;

	// Externalize variables for performance
	public static final File ROOT_DIR = new File(Environment.getExternalStorageDirectory() + File.separator + "SMStranslator");
	public static final String FILE_NAME = "textToSpeech.xml";

	private File m_VXML_File;
	private BufferedWriter buf;
	private FileWriter fw;

	public VXMLGenerator(){

	}

	public void generateFile(String _inString){

		if(!ROOT_DIR.exists())
			ROOT_DIR.mkdirs();

		writeTextToFile(_inString);
	}

	private void writeTextToFile(String _inString){
		String base = "<vxml version=\"2.1\">" +
				"<meta name=\"maintainer\" content=\"YOUREMAILADDRESS@HERE.com\"/>" +
				"	<form>" +
				"		<block>"+_inString+"</block>" +
				"	</form>" +
				"</vxml>";

		try{
			if(ROOT_DIR.canWrite()){
				m_VXML_File = new File(ROOT_DIR,FILE_NAME);
				if (!m_VXML_File.exists())
					m_VXML_File.createNewFile();

				//BufferedWriter for performance, true to set append to file flag
				fw = new FileWriter(m_VXML_File, false);
				buf = new BufferedWriter(fw);
				buf.write(base);
				buf.close();
			}
		}
		catch (IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
