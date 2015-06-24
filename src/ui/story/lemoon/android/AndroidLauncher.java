package ui.story.lemoon.android;

import android.os.Bundle;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import ui.story.lemoon.BsuEvent;
import ui.story.lemoon.nouse_MainUI;
import ui.story.lemoon.MyGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
	    config.useCompass = false;
 
	   
//		initialize(new MainUI(new BsuEvent(){
//			@Override
//			public void notify(Object obj, String msg) {
//				//Toast.makeText(AndroidLauncher.this, "libgdx消息通知", Toast.LENGTH_SHORT).show();
//			}
//        }), config);
	    
	    initialize(new MyGame(new BsuEvent(){
			@Override
			public void notify(Object obj, String msg) {
				//Toast.makeText(AndroidLauncher.this, "libgdx消息通知", Toast.LENGTH_SHORT).show();
			}
        }), config);
	}
}
