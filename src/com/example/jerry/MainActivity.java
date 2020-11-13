package com.example.jerry;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends Activity {
	static final int check=1111;
	BluetoothAdapter mBluetoothAdapter;
	BluetoothSocket mmSocket;
	BluetoothDevice mmDevice;
	OutputStream mmOutputStream;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button bt=(Button)findViewById(R.id.button1);
		ImageButton halt=(ImageButton)findViewById(R.id.haltbt);
		ImageButton up=(ImageButton)findViewById(R.id.upbt);
		ImageButton back=(ImageButton)findViewById(R.id.backbt);
		ImageButton left=(ImageButton)findViewById(R.id.leftbt);
		ImageButton right=(ImageButton)findViewById(R.id.rightbt);
		
		
		 mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		    if(mBluetoothAdapter == null) {
		      Toast.makeText(getApplicationContext(), "No bluetooth adaptor found!", Toast.LENGTH_LONG).show();
		    } 
		 
		    if(!mBluetoothAdapter.isEnabled()) {
		      Intent enableBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		      startActivityForResult(enableBluetooth, 0);
		    } 
		 
		    Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
		    if(pairedDevices.size() > 0) {
		      for(BluetoothDevice device : pairedDevices) {
		        if(device.getName().equals("HC-05")) {
		         Toast.makeText(this, "Bluetooth device found!", Toast.LENGTH_LONG).show();
		          mmDevice = device;
		          break; 
		        } 
		      } 
		    } 
		   // myLabel.setText("Bluetooth Device Found");
		    
		    UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID
		    try{
		    mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);    
		    mmSocket.connect();
		    mmOutputStream = mmSocket.getOutputStream();
		    Toast.makeText(this, "Bluetooth connected!", Toast.LENGTH_LONG).show();
		    }
		    catch(IOException ex){
		    	Toast.makeText(this, "Bluetooth NOT connected!", Toast.LENGTH_LONG).show();
		    }
		   // mmInputStream = mmSocket.getInputStream();
		   // beginListenForData(); 
		   // myLabel.setText("Bluetooth Opened");
		
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		        //intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
		        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);
		        startActivityForResult(intent, check);
				
			}
		});
		
		//go straight button
		up.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Go straight pressed!", Toast.LENGTH_SHORT).show();
				try{
					 mmOutputStream.write('s');
					 //Toast.makeText(getApplicationContext(), "Go straight pressed!", Toast.LENGTH_SHORT).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(getApplicationContext(), "Sending failed!", Toast.LENGTH_LONG).show();
				 }
				
			}
		});
		
		//go right button
		right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Turn right pressed!", Toast.LENGTH_SHORT).show();
				try{
					 mmOutputStream.write('r');
					 //Toast.makeText(getApplicationContext(), "Turn right pressed!", Toast.LENGTH_SHORT).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(getApplicationContext(), "Sending failed!", Toast.LENGTH_LONG).show();
				 }
				
			}
		});
		
		
		//go left button
		left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Turn left pressed!", Toast.LENGTH_SHORT).show();
				try{
					 mmOutputStream.write('l');
					 //Toast.makeText(getApplicationContext(), "Turn left pressed!", Toast.LENGTH_SHORT).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(getApplicationContext(), "Sending failed!", Toast.LENGTH_LONG).show();
				 }
				
			}
		});
		
		
		//go back button
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Reverse gear pressed!", Toast.LENGTH_SHORT).show();
				try{
					 mmOutputStream.write('b');
					 //Toast.makeText(getApplicationContext(), "Reverse gear pressed!", Toast.LENGTH_SHORT).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(getApplicationContext(), "Sending failed!", Toast.LENGTH_LONG).show();
				 }
				
			}
		});
		
		
		//halt button
		halt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getApplicationContext(), "Stopped!", Toast.LENGTH_SHORT).show();
				try{
					 mmOutputStream.write('h');
					 //Toast.makeText(getApplicationContext(), "Stopped!", Toast.LENGTH_SHORT).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(getApplicationContext(), "Sending failed!", Toast.LENGTH_LONG).show();
				 }
				
			}
		});
		
	}
	
	
	
	

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		// TODO Auto-generated method stub
		if(requestCode == check && resultCode == RESULT_OK){
			ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			//Toast.makeText(this, matches.get(0), Toast.LENGTH_LONG).show();
			
			//String[] arr=(matches.get(0)).split(" ");
			
			if (matches.get(0).contains("straight")){
				
				 try{
					 mmOutputStream.write('s');
					 Toast.makeText(this, "Going Straight!", Toast.LENGTH_LONG).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(this, "Sending failed!", Toast.LENGTH_LONG).show();
				 }
			}
			
			else if (matches.get(0).contains("right")){
				try{
					 mmOutputStream.write('r');
					 Toast.makeText(this, "Turning Right!", Toast.LENGTH_LONG).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(this, "Sending failed!", Toast.LENGTH_LONG).show();
				 }
				
			}
			
			else if (matches.get(0).contains("left")){
				try{
					 mmOutputStream.write('l');
					 Toast.makeText(this, "Turning Left!", Toast.LENGTH_LONG).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(this, "Sending failed!", Toast.LENGTH_LONG).show();
				 }
			}
			
			else if (matches.get(0).contains("back")){
				try{
					 mmOutputStream.write('b');
					 Toast.makeText(this, "Reverse Gear!", Toast.LENGTH_LONG).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(this, "Sending failed!", Toast.LENGTH_LONG).show();
				 }
			}
			
			else if (matches.get(0).contains("stop")){
				try{
					 mmOutputStream.write('h');
					 Toast.makeText(this, "Stopping!", Toast.LENGTH_LONG).show();
				 }
				 catch(IOException ex){
					 Toast.makeText(this, "Sending failed!", Toast.LENGTH_LONG).show();
				 }
			}
			
			else{
				Toast.makeText(this, "Give me a valid direction!", Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
