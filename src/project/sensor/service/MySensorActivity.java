package project.sensor.service;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MySensorActivity extends Activity {
    /** Called when the activity is first created. */
	
	Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        i=new Intent(this, SensorService.class);
    }
    
    public void onStart(View arg) {
		Toast.makeText(getApplicationContext(),"Start Service",Toast.LENGTH_SHORT).show();
		startService(i);
	}
	
	public void onStop(View arg) {
		Toast.makeText(getApplicationContext(),"Stop Service",Toast.LENGTH_SHORT).show();
		stopService(i);
	}
}