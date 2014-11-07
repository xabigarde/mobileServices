package at.fhooe.ms.smstranslate;

import java.util.Observable;
import java.util.Observer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import at.fhooe.ms.translator.Languages;
import at.fhooe.ms.translator.Translator;

public class DummyTranslatorActivity extends Activity implements Observer {

	Translator mTranslator;
	TextView mInputText,mOutputText;
	Button mTranslateButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dummy_translator);
		setTranslator();
		setUI();
		
	}

	/**
	 * 
	 */
	private void setUI() {
		mInputText= (EditText) findViewById(R.id.editText1);
		mOutputText=(EditText) findViewById(R.id.editText2);
		mTranslateButton= (Button)findViewById(R.id.button1);
		mTranslateButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mTranslator.translate(mInputText.getText().toString(), Languages.ENGLISH, Languages.GERMAN);
			}
		});
		
		}


	/**
	 * 
	 */
	private void setTranslator() {
		mTranslator= new Translator(this);
		mTranslator.addObserver(this);		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dummy_translator, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/* (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable observable, Object data) {
		mOutputText.setText(data.toString());
	}
}
