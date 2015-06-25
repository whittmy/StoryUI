package ui.story.lemoon.android;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;
 
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ui.story.lemoon.BsuEvent;
import ui.story.lemoon.MyGame;
import ui.story.lemoon.msg.MyMsg;
import ui.story.lemoon.nouse.nouse_MainUI;


public class AndroidLauncher extends AndroidApplication {
	class BatteryReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int status = intent.getIntExtra("status", 1); // v1
			int batteryLevel = intent.getIntExtra("level", 0); // v0

			if (status == 2) {
//				leftBatteryImg.setImageResource(R.drawable.battery_charging_100);
//				rightBatteryImg.setImageResource(R.drawable.battery_charging_100);
//				ivPlayerBatteryImg.setImageResource(R.drawable.battery_charging_100);
			} else if (batteryLevel <= 0xa) {
//				leftBatteryImg.setImageResource(R.drawable.player_battery_10);
//				rightBatteryImg.setImageResource(R.drawable.player_battery_10);
//				ivPlayerBatteryImg.setImageResource(R.drawable.player_battery_10);
			} else if (batteryLevel == 0x19) {
//				showVoiceDialog(getString(R.string.player_battery_remind));
//				resetControlHideTime(0xea60);
			} else if (batteryLevel <= 0x1e) {
//				leftBatteryImg.setImageResource(R.drawable.player_battery_20);
//				rightBatteryImg.setImageResource(R.drawable.player_battery_20);
//				ivPlayerBatteryImg.setImageResource(R.drawable.player_battery_20);
			} else if (batteryLevel <= 0x3c) {
//				leftBatteryImg.setImageResource(R.drawable.player_battery_50);
//				rightBatteryImg.setImageResource(R.drawable.player_battery_50);
//				ivPlayerBatteryImg.setImageResource(R.drawable.player_battery_50);
			} else if (batteryLevel <= 0x5a) {
//				leftBatteryImg.setImageResource(R.drawable.player_battery_80);
//				rightBatteryImg.setImageResource(R.drawable.player_battery_80);
//				ivPlayerBatteryImg.setImageResource(R.drawable.player_battery_80);
			} else {
//				leftBatteryImg.setImageResource(R.drawable.player_battery_100);
//				rightBatteryImg.setImageResource(R.drawable.player_battery_100);
//				ivPlayerBatteryImg.setImageResource(R.drawable.player_battery_100);
			}

			// goto0
			if (batteryLevel != 0) {
//				leftBatteryTv.setText(String.valueOf(batteryLevel) + "%");
//				rightBatteryTv.setText(String.valueOf(batteryLevel) + "%");
//				tvPlayerBatteryValue.setText(String.valueOf(batteryLevel) + "%");
			}
			// cond0
			return;
		}
	}	
	
	
	
	private BatteryReceiver batteryReceiver;
	
	
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
	    config.useCompass = false;
	    
		batteryReceiver = new BatteryReceiver();
		
		//ntp对时
		Intent intent=new Intent("adjusttime.lemoon.SNTP");    
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     
		ComponentName cn=new ComponentName("adjusttime.lemoon","adjusttime.lemoon.SNTP");    
		intent.setComponent(cn);    
		startService(intent);
		
		
	    initialize(new MyGame(new BsuEvent(){
			@Override
			public void notify(MyMsg msg) {
				Intent it;
				ComponentName com;
				switch(msg.what){
				case MyMsg.ITEM_GUOXUE:
					//=============规定： 我们的资源分类 从10000开始   ============================
					System.out.println("GUOXUE");
					
					it = new Intent();
					com= new ComponentName("children.lemoon", "children.lemoon.categrid.MoviesGridActivity");  
					it.setComponent(com);  
					
					it.putExtra("curCata", "国学教育");
					it.putExtra("cataId", 10000009);				
					
					startActivity(it);
					break;
				case MyMsg.ITEM_CARTON:
					System.out.println("CARTON");
					it = new Intent();
					com= new ComponentName("children.lemoon", "children.lemoon.categrid.MoviesGridActivity");  
					it.setComponent(com); 
					it.putExtra("curCata",  "动画城");
					it.putExtra("cataId", 10000009);
					break;
				case MyMsg.ITEM_LANGUAGE:
					System.out.println("LANGUAGE");
					it = new Intent();
					com= new ComponentName("children.lemoon", "children.lemoon.categrid.MoviesGridActivity");  
					it.setComponent(com); 
					it.putExtra("curCata",  "语言发展");
					it.putExtra("cataId", 10000009);				
					
					startActivity(it);	
					break;
				case MyMsg.ITEM_LIFE:
					System.out.println("LIFE");
					it = new Intent();
					com= new ComponentName("children.lemoon", "children.lemoon.categrid.MoviesGridActivity");  
					it.setComponent(com); 
					it.putExtra("curCata",  "生活常识");
					it.putExtra("cataId", 10000009);				
					
					startActivity(it);
					break;
				case MyMsg.ITEM_LOCAL:
					System.out.println("LOCAL");
					break;
				case MyMsg.ITEM_MATHS:
					System.out.println("MATHS");
					it = new Intent();
					com= new ComponentName("children.lemoon", "children.lemoon.categrid.MoviesGridActivity");  
					it.setComponent(com); 
					it.putExtra("curCata",  "数理思维");
					it.putExtra("cataId", 10000009);				
					
					startActivity(it);		
					break;
				case MyMsg.ITEM_MUSIC:
					System.out.println("MUSIC");
					it = new Intent();
					com= new ComponentName("children.lemoon", "children.lemoon.music.MuPlayer");  
					it.setComponent(com); 
 
					it.putExtra("curCata",  "网络音乐测试");
					it.putExtra("cataId", 100);
					startActivity(it);
					
					
					break;
				case MyMsg.ITEM_PAINT:
					System.out.println("PAINT");
					break;
				case MyMsg.ITEM_SCIENCE:
					System.out.println("SCIENCE");
					it = new Intent();
					com= new ComponentName("children.lemoon", "children.lemoon.categrid.MoviesGridActivity");  
					it.setComponent(com); 
					it.putExtra("curCata",  "自然科学");
					it.putExtra("cataId", 10000009);				
					
					startActivity(it);	
					break;
				case MyMsg.ITEM_SETTING:
					System.out.println("SETTING");
					break;
				}
			}
        }), config);
	}
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
        try {
            unregisterReceiver(batteryReceiver);
        }
        catch(IllegalArgumentException illegalargumentexception) {
            if(!illegalargumentexception.getMessage().contains("Receiver not registered"))
                throw illegalargumentexception;
        }
        
		super.onPause();
	}
	
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		IntentFilter filter = new IntentFilter("android.intent.action.BATTERY_CHANGED");
		registerReceiver(batteryReceiver, filter);
		
		super.onResume();
	}
	
 
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		
		System.exit(0);
	}
}
