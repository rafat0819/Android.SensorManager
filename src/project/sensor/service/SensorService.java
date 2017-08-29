package project.sensor.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.IBinder;
import android.widget.Toast;

public class SensorService extends Service implements SensorEventListener {
	
	SensorManager sensor;
	AudioManager audio;
	Sensor Accelerometer,Proximity;
	boolean dark=false; 
	boolean flipped=false;
	boolean night=false;
	boolean upsideDown=false;
	public void onCreate(){
		Toast.makeText(this, "Created", Toast.LENGTH_SHORT).show();
		audio=(AudioManager)getSystemService(Context.AUDIO_SERVICE);
		sensor=(SensorManager)getSystemService(SENSOR_SERVICE);
        
        Accelerometer = sensor.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        Proximity = sensor.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        
        if (Accelerometer != null){
        	Toast.makeText(this,"Accelerometer Sensor Found",Toast.LENGTH_SHORT).show();
        	sensor.registerListener(this,Accelerometer,SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
        	Toast.makeText(this,"Accelerometer Sensor Not Found",Toast.LENGTH_SHORT).show();
        }
        
        if (Proximity != null){
        	
        	Toast.makeText(this,"Proximity Sensor Found",Toast.LENGTH_SHORT).show();
        	sensor.registerListener(this,Proximity,SensorManager.SENSOR_DELAY_NORMAL);
            
        }
        else {
        	Toast.makeText(this,"Proximity Sensor Not Found",Toast.LENGTH_SHORT).show();
        }
        
	}
	
	
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
	}

	public void onSensorChanged(SensorEvent e) {
		// TODO Auto-generated method stub
		
     
		
		if (e.sensor.getType() == Sensor.TYPE_ACCELEROMETER ) {
            if(e.values[1] <= -9){
            	upsideDown=true;
            }
            else {
            	upsideDown =false;
            }
            if(e.values[2]<= -9){
            	flipped=true;
            }
            else{
            	flipped=false;
            }
        }
		
		
		if (e.sensor.getType() == Sensor.TYPE_PROXIMITY ) {
			if (e.values[0]<= 5) {
				dark=true;
            }
            else{
            	dark=false;
            }
        }
		
		if(getTime()=="12:00:00"){
			night=true;
		}
		if(getTime()=="06:00:00"){
			night=false;
		}
		
		
		if (flipped){
			audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			Toast.makeText(this,"Busy",Toast.LENGTH_SHORT).show();
		}
		if (!flipped && !upsideDown){
			audio.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			Toast.makeText(this,"In Hand",Toast.LENGTH_SHORT).show();
		}
		if(upsideDown){
			Toast.makeText(this,"In Pocket",Toast.LENGTH_SHORT).show();
			audio.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			
		}
		if(night){
			Toast.makeText(this,"timesup true",Toast.LENGTH_SHORT).show();
			audio.setRingerMode(AudioManager.RINGER_MODE_SILENT);
		}
	}
	
	
	public String getTime(){
		Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("HH:mm:ss");
        String strDate=mdformat.format(calendar.getTime());
        return strDate;
	}
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent,int flags,int Id)
    {
        return START_STICKY;
    }
    public void onDestroy()

    {
    	Toast.makeText(this, "Destroyed", Toast.LENGTH_SHORT).show();
        sensor.unregisterListener(this);
        

    }
}
