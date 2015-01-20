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
	private static final String YANDEX_LANGUAGE="&lang=";
	private static final String YANDEX_TEXT="&text=";
	
	private static final String YANDEX_LANGUAGE_REQUEST_URL="https://translate.yandex.net/api/v1.5/tr.json/detect?"+YANDEX_KEY+"&text=";
	private static final int YANDEX_REQUEST_LANGUAGE=1;
	private static final int YANDEX_REQUEST_TRANSLATION=2;
	private Context mContext;
	private String mTranslatedText;
	private String outputLanguage;
	/**
	 * 
	 */
	public Translator(Context _context){
		mContext=_context;
	}
	
	/**
	 * Creates the Web-URL for the JSON Request and sends it to the YANDEX-Server
	 * @param _textInput
	 * @param _inputLanguage
	 * @param _outputLanguage
	 */
	public void translate(String _textInput, String _inputLanguage, String _outputLanguage){

		String formatedText=formatText(_textInput);
		outputLanguage=_outputLanguage;
		if(_inputLanguage.contains(Languages.AUTOMATIC)){
			String address = buildLanguageDetectionRequest(formatedText);
			sendText(address, YANDEX_REQUEST_LANGUAGE);
		}else{
		String address= buildTranslationRequest(formatedText, _inputLanguage, _outputLanguage);
		sendText(address, YANDEX_REQUEST_TRANSLATION);
		}
		
	}
	

	/**
	 * Sends text to the Yandex-server as a JSON Object request via HTTP. Notifies the observers, when the translated text is received.
	 * @param _address http url that is send to the server.
	 */
	private void sendText(String _address, int _requestType ) {
		JsonObjectRequest jsObjRequest;
		if(_requestType== YANDEX_REQUEST_TRANSLATION){
		 jsObjRequest=getTranslationRequest(_address);
		}else{
			jsObjRequest=getLanguageRequest(_address);
		}
		MySingleton.getInstance(mContext).addToRequestQueue(jsObjRequest);
		RequestQueue queue=MySingleton.getInstance(mContext.getApplicationContext()).getRequestQueue();
		queue.start();
		
		
	}

	/**
	 * @param get
	 * @param _address
	 * @param object
	 * @return
	 */
	private JsonObjectRequest getTranslationRequest(String _address) {
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, _address, null, new Response.Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject _response) {
				try {
					String a= _response.getString("text").replace("[\"", "");
					String b= a.replace("\"]", "");
					setTranslatedText(b);
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
		return jsObjRequest;
	}
	
	private JsonObjectRequest getLanguageRequest(final String _address) {
		JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, _address, null, new Response.Listener<JSONObject>() {
			String text=_address.replace(YANDEX_LANGUAGE_REQUEST_URL, "");
			@Override
			public void onResponse(JSONObject _response) {
				try {
					String a= _response.getString("lang").replace("\"", "");
					String b= a.replace("\"", "");
					String address= buildTranslationRequest(text, b, outputLanguage);
					sendText(address, YANDEX_REQUEST_TRANSLATION);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
			}

		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0) {
				Log.d("JSON_OUTPUT", arg0.getMessage());
			}
		});
		return jsObjRequest;
	}
	
	
	

	/**
	 * Builds the http request url.
	 * @param _textInput the text to translate
	 * @param english original language
	 * @param german target language
	 */
	private String formatText(String _textInput) {
		
		String[] formatedTextArray=_textInput.split(" ");
		String formatedText="";
		
		for(int i=0;i<formatedTextArray.length;i++){
			if(i!=0){
				formatedText+="+";
			}
			formatedText+=formatedTextArray[i];
		}
		return formatedText;

		
	}
	
	private String buildTranslationRequest(String _formatedText, String _input, String _output){
		return YANDEX_URL_FIRST_PART+YANDEX_KEY+YANDEX_LANGUAGE+_input+"-"+_output +YANDEX_TEXT+_formatedText;
		
	}
	
	private String buildLanguageDetectionRequest(String formatedText){
		
		return YANDEX_LANGUAGE_REQUEST_URL+formatedText;
	}



	/**
	 * @return the translatedText
	 */
	public String getTranslatedText() {
		return mTranslatedText;
	}

	/**
	 * @param _translatedText the translatedText to set
	 */
	public void setTranslatedText(String _translatedText) {
		this.mTranslatedText = _translatedText;
	}
	
}
