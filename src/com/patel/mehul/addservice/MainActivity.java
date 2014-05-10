package com.patel.mehul.addservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.patel.mehul.addservice.AddService.LocalBinder;

public class MainActivity extends Activity {

	
	EditText etA, etB, etResult;
	Button bResult;
	
	//Connection
	AddService mService;
	boolean mBound;
	
	ServiceConnection mConnect = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			mBound = false;
			//mService = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			mBound = true;
			LocalBinder binder = (LocalBinder)service;
			mService = binder.getService();
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etA = (EditText) findViewById(R.id.etA);
		etB = (EditText) findViewById(R.id.etB);
		etResult = (EditText) findViewById(R.id.etResult);
		
		bResult = (Button) findViewById(R.id.bResult);
		
		bResult.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				int a = Integer.parseInt(etA.getText().toString());
				int b = Integer.parseInt(etB.getText().toString());
				
				int r = mService.add(a, b);
				
				etResult.setText(String.valueOf(r));
				
	
			}
		});
		/*if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
	}
	
	@Override
	protected void onStart() {
		
		super.onStart();
		
		Intent i = new Intent(this, AddService.class);
		bindService(i, mConnect, BIND_AUTO_CREATE);
		
	}
	
	@Override
	protected void onStop() {
		
		super.onStop();
		
		if(mBound){
			mService.unbindService(mConnect);
			mBound = false;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
