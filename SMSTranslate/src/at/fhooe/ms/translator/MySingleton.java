/**
 * 
 */
package at.fhooe.ms.translator;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * @author Viktor
 *
 */
public class MySingleton {

private static MySingleton mInstance;
	private RequestQueue mRequestQueue;
	private static Context mCtx; 
	
	/**
	 * 
	 * @param _context
	 */
	private MySingleton(Context _context){
		mCtx= _context;
		mRequestQueue=getRequestQueue();
		
		
	}
	/**
	 * 
	 * @param context
	 * @return
	 */
	public static synchronized MySingleton getInstance(Context context){
		if(mInstance == null){
			mInstance= new MySingleton(context);
		}
		return mInstance;
	}
	/**
	 * 
	 * @return
	 */
	public RequestQueue getRequestQueue(){
		if(mRequestQueue == null){
			mRequestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
		}
		return mRequestQueue;
	}
	/**
	 * 
	 * @param _req
	 */
	public <T> void addToRequestQueue(Request<T> _req){
		getRequestQueue().add(_req);
	}
}
