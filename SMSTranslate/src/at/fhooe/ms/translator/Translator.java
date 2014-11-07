package at.fhooe.ms.translator;

import java.util.Observable;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
 
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError; 
import com.android.volley.toolbox.JsonObjectRequest;


public class Translator extends Observable{

	//private static final String YANDEX_USER="mcm.mobileservices:smstranslate";
	private static final String YANDEX_KEY = "key=trnsl.1.1.20141106T000651Z.ba863ed5d1024604.bbdd558221c6c57887254d9d2cbe6054b0e7cc5a";
	private static final String YANDEX_URL_FIRST_PART="https://translate.yandex.net/api/v1.5/tr.json/translate?";
	private static final String YANDEX_LANGUAGE="&lang=en-de";
	private static final String YANDEX_TEXT="&text=";
	private Context mContext;
	private String mTranslatedText;
	/**
	 * 
	 */
	public Translator(Context _context){
		mContext=_context;
	}
	
	/**
	 * Creates the Web-URL for the JSON Request and sends it to the YANDEX-Server
	 * @param _textInput
	 * @return the m_translatedText
	 */
	public void translate(String _textInput, int _origLanguage, int _targetLanguage){
		
		String address=buildAddress(_textInput, _origLanguage, _targetLanguage );
		sendText(address);
		
	}

	/**
	 * Sends text to the Yandex-server as a JSON Object request via HTTP. Notifies the observers, when the translated text is received.
	 * @param _address http url that is send to the server.
	 */
	private void sendText(String _address) {
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, _address, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject _response) {
				try {
					setTranslatedText(_response.getString("text"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				setChanged();
				notifyObservers(mTranslatedText);
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Log.d("JSON_OUTPUT", arg0.getMessage());
			}
		});
		MySingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
		RequestQueue queue=MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();
		queue.start();
		
	}

	/**
	 * Builds the http request url.
	 * @param _textInput the text to translate
	 * @param _origLanguage original language
	 * @param _targetLanguage target language
	 */
	private String buildAddress(String _textInput, int _origLanguage,
			int _targetLanguage) {
		
		String[] formatedTextArray=_textInput.split(" ");
		String formatedText="";
		
		for(int i=0;i<formatedTextArray.length;i++){
			if(i!=0){
				formatedText+="+";
			}
			formatedText+=formatedTextArray[i];
		}
		String address=YANDEX_URL_FIRST_PART+YANDEX_KEY+YANDEX_LANGUAGE+YANDEX_TEXT+formatedText;
			return address; 
	}

	/**
	 * @return the translatedText
	 */
	public String getTranslatedText() {
		return mTranslatedText;
	}

	/**
	 * @param translatedText the translatedText to set
	 */
	public void setTranslatedText(String translatedText) {
		this.mTranslatedText = translatedText;
	}
	
}
